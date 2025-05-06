package com.group05.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.group05.dto.ProductTaxResponseDTO;
import com.group05.model.ProductTax;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductTaxMapper {

    ProductTaxResponseDTO toDto(ProductTax productTax);
}
