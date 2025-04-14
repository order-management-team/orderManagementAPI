package com.group05.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.group05.constant.ExceptionMessages;
import com.group05.dto.ProductRequestDTO;
import com.group05.dto.ProductResponseDTO;
import com.group05.exceptionHandler.exceptions.ResourceNotFoundException;
import com.group05.mapper.ProductMapper;
import com.group05.model.Product;
import com.group05.repository.ProductRepository;
import com.group05.service.use_cases.ProductUseCase;

@Service
public class ProductService implements ProductUseCase {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductResponseDTO> getProducts() {
        return productRepository.findAllByOrderById().stream().map(productMapper::toDto).toList();
    }

    @Override
    public ProductResponseDTO getProductDetail(Long id) {
        return productMapper.toDto(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ExceptionMessages.PRODUCT_NOT_FOUND, id))));
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = productRepository.save(productMapper.toEntity(productRequestDTO));
        return productMapper.toDto(product);
    }

    @Override
    public ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO, Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ExceptionMessages.PRODUCT_NOT_FOUND, id)));

        Product productToUpdate = productMapper.toEntity(productRequestDTO);
        productToUpdate.setId(id);

        Product productUpdated = productRepository.save(productToUpdate);
        return productMapper.toDto(productUpdated);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ExceptionMessages.PRODUCT_NOT_FOUND, id)));
        productRepository.delete(product);
    }

}
