package com.group05.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.group05.dto.ProductTaxResponseDTO;
import com.group05.model.ProductTax;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductTaxMapper {

    @Mapping(target = "taxId", source = "tax.id")
    @Mapping(target = "name", source = "tax.name")
    @Mapping(target = "rate", source = "tax.rate") 
    ProductTaxResponseDTO toDto(ProductTax productTax);
}
