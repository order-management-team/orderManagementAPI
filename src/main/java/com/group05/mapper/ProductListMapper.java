package com.group05.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.group05.dto.ProductListResponseDTO;
import com.group05.model.Product;

@Mapper(componentModel = "spring", uses = {ProductTaxMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductListMapper {
    ProductListResponseDTO toDto(Product product);
}
