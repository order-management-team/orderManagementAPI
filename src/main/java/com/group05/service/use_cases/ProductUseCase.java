package com.group05.service.use_cases;

import java.util.List;

import com.group05.dto.ProductRequestDTO;
import com.group05.dto.ProductResponseDTO;

public interface ProductUseCase {
    List<ProductResponseDTO> getProducts();
    ProductResponseDTO getProductDetail(Long id);
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO, Long id);
    void deleteProduct(Long id);
}
