package com.group05.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.group05.dto.ProductRequestDTO;
import com.group05.dto.ProductResponseDTO;
import com.group05.model.Product;

@Mapper(componentModel = "spring", uses = {ProductTaxMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "productTaxes", source = "productTaxes")
    ProductResponseDTO toDto(Product product);

    Product toEntity(ProductRequestDTO productRequestDTO);
}
