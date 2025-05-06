package com.group05.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group05.constant.ExceptionMessages;
import com.group05.constant.RecordStateConstants;
import com.group05.dto.PageResponseDTO;
import com.group05.dto.PaginationResponseDTO;
import com.group05.dto.ProductListResponseDTO;
import com.group05.dto.ProductRequestDTO;
import com.group05.dto.ProductResponseDTO;
import com.group05.exceptionHandler.exceptions.ResourceNotFoundException;
import com.group05.mapper.ProductListMapper;
import com.group05.mapper.ProductMapper;
import com.group05.mapper.ProductTaxMapper;
import com.group05.model.Product;
import com.group05.model.ProductPrice;
import com.group05.model.ProductTax;
import com.group05.model.Tax;
import com.group05.repository.ProductRepository;
import com.group05.repository.ProductTaxRepository;
import com.group05.repository.TaxRepository;
import com.group05.service.use_cases.ProductPriceUseCase;
import com.group05.service.use_cases.ProductUseCase;


@Service
public class ProductServiceImpl implements ProductUseCase {

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private ProductListMapper productListMapper;
    private ProductPriceUseCase productPriceUseCase;
    private TaxRepository taxRepository;
    private ProductTaxMapper productTaxMapper;
    private ProductTaxRepository productTaxRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, 
    ProductPriceUseCase productPriceUseCase, TaxRepository taxRepository,
    ProductListMapper productListMapper, ProductTaxMapper productTaxMapper, ProductTaxRepository productTaxRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productPriceUseCase = productPriceUseCase;
        this.taxRepository = taxRepository;
        this.productListMapper = productListMapper;
        this.productTaxMapper = productTaxMapper;
        this.productTaxRepository = productTaxRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDTO<List<ProductListResponseDTO>> getProducts(Long pageNumber, Long pageSize) {
        Pageable pageable = PageRequest.of(Math.toIntExact(pageNumber), Math.toIntExact(pageSize));

        // Consulta paginada
        Page<Product> productsPage = productRepository.findByFlgStateOrderById(pageable, RecordStateConstants.ACTIVE);

        // Mapeo de productos
        List<ProductListResponseDTO> productsDTO = productsPage.getContent().stream()
            .map(product -> {
                ProductListResponseDTO dto = productListMapper.toDto(product);
                dto.setPrice(productPriceUseCase.getCurrentPrice(product));
                return dto;
            })
            .toList();

        // Construcción del paginado
        PageResponseDTO pageResponseDTO = new PageResponseDTO();
        pageResponseDTO.setSize((long) productsPage.getSize());
        pageResponseDTO.setTotalElements(productsPage.getTotalElements());
        pageResponseDTO.setTotalPages((long) productsPage.getTotalPages());
        pageResponseDTO.setNumber((long) productsPage.getNumber());

        // Respuesta final
        PaginationResponseDTO<List<ProductListResponseDTO>> output = new PaginationResponseDTO<>();
        output.setContent(productsDTO);
        output.setPage(pageResponseDTO);

        return output;
    }


    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductDetail(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format(ExceptionMessages.PRODUCT_NOT_FOUND, id)));

        ProductResponseDTO response = productMapper.toDto(product);
        response.setPrice(productPriceUseCase.getCurrentPrice(product)); 
        
        return response;
    }

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toEntity(productRequestDTO);

        ProductPrice productPrice = new ProductPrice();
        productPrice.setPrice(productRequestDTO.getPrice());
        productPrice.setActivePrice(true);
        productPrice.setProduct(product);

        product.getPrices().add(productPrice);

        if(productRequestDTO.getTaxesId() != null && !productRequestDTO.getTaxesId().isEmpty()){
            List<Tax> taxes = taxRepository.findAllById(productRequestDTO.getTaxesId());

            List<ProductTax> productTaxes = taxes.stream().map(
                tax -> {
                    ProductTax pt = new ProductTax();
                    pt.setProduct(product);
                    pt.setTax(tax);
                    return pt;
                }
            ).collect(Collectors.toList());

            product.setProductTaxes(productTaxes);
        }

        Product saved = productRepository.save(product);

        ProductResponseDTO response = productMapper.toDto(saved);
        response.setPrice(productPriceUseCase.getCurrentPrice(saved));

        return response;
    }
    
    @Override
    @Transactional
    public ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO, Long id) {
        // Buscar producto existente
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format(ExceptionMessages.PRODUCT_NOT_FOUND, id)));

        // Actualizar atributos simples
        existingProduct.setName(productRequestDTO.getName());
        existingProduct.setStock(productRequestDTO.getStock());
        existingProduct.setDescription(productRequestDTO.getDescription());

        // Validar si el precio ha cambiado
        BigDecimal currentPrice = productPriceUseCase.getCurrentPrice(existingProduct);
        if (currentPrice == null || currentPrice.compareTo(productRequestDTO.getPrice()) != 0) {
            // Desactivar precio activo actual
            existingProduct.getPrices().stream()
                .filter(ProductPrice::isActivePrice)
                .forEach(p -> {
                    p.setActivePrice(false);
                    p.setEffectiveTo(LocalDate.now());
                });

            // Crear nuevo precio activo
            ProductPrice newPrice = new ProductPrice();
            newPrice.setPrice(productRequestDTO.getPrice());
            newPrice.setActivePrice(true);
            newPrice.setEffectiveFrom(LocalDate.now());
            newPrice.setProduct(existingProduct);
            existingProduct.getPrices().add(newPrice);
        }

        // Obtener impuestos entrantes (nuevos o persistentes)
        Set<Long> incomingTaxIds = new HashSet<>(
            productRequestDTO.getTaxesId() != null ? productRequestDTO.getTaxesId() : List.of()
        );
        List<Tax> taxes = taxRepository.findAllById(incomingTaxIds);

        // Obtener impuestos actualmente activos desde BD
        List<ProductTax> currentActiveTaxes = productTaxRepository
            .findByProductIdAndFlgState(id, RecordStateConstants.ACTIVE);
        Set<Long> existingActiveTaxIds = currentActiveTaxes.stream()
            .map(pt -> pt.getTax().getId())
            .collect(Collectors.toSet());

        // Desactivar impuestos que ya no están en la nueva lista
        for (ProductTax pt : currentActiveTaxes) {
            if (!incomingTaxIds.contains(pt.getTax().getId())) {
                pt.setFlgState(RecordStateConstants.INACTIVE);
            }
        }

        // Identificar nuevos impuestos a agregar
        Set<Long> newTaxIds = new HashSet<>(incomingTaxIds);
        newTaxIds.removeAll(existingActiveTaxIds);

        for (Tax tax : taxes) {
            if (newTaxIds.contains(tax.getId())) {
                ProductTax newPt = new ProductTax();
                newPt.setProduct(existingProduct);
                newPt.setTax(tax);
                newPt.setFlgState(RecordStateConstants.ACTIVE);
                existingProduct.getProductTaxes().add(newPt);
            }
        }

        // Persistir cambios
        Product saved = productRepository.save(existingProduct);

        // Mapear y construir respuesta
        ProductResponseDTO response = productMapper.toDto(saved);
        response.setPrice(productPriceUseCase.getCurrentPrice(saved)); // Precio actualizado

        // Solo impuestos activos en la respuesta
        List<ProductTax> activeTaxes = productTaxRepository
            .findByProductIdAndFlgState(saved.getId(), RecordStateConstants.ACTIVE);
        response.setProductTaxes(activeTaxes.stream().map(productTaxMapper::toDto).toList());

        return response;
    }


    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format(ExceptionMessages.PRODUCT_NOT_FOUND, id)));
    
        product.setFlgState(RecordStateConstants.INACTIVE); 

        product.getProductTaxes().forEach(pt -> pt.setFlgState(RecordStateConstants.INACTIVE));
        product.getPrices().forEach(price -> price.setActivePrice(false));
    
        productRepository.save(product);
    }
    

}
