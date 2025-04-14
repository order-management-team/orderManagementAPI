package com.group05.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.group05.dto.ProductRequestDTO;
import com.group05.dto.ProductResponseDTO;
import com.group05.model.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductResponseDTO toDto(Product product);

    Product toEntity(ProductRequestDTO productRequestDTO);
}
