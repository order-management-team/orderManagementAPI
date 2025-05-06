package com.group05.service.use_cases;

import java.util.List;

import com.group05.dto.PaginationResponseDTO;
import com.group05.dto.ProductListResponseDTO;
import com.group05.dto.ProductRequestDTO;
import com.group05.dto.ProductResponseDTO;

public interface ProductUseCase {
    PaginationResponseDTO<List<ProductListResponseDTO>> getProducts(Long pageNumber, Long pageSize);
    ProductResponseDTO getProductDetail(Long productId);
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO, Long productId);
    void deleteProduct(Long productId);
}
