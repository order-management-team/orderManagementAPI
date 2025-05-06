package com.group05.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.group05.dto.OrderItemRequestDTO;
import com.group05.dto.OrderItemResponseDTO;
import com.group05.model.OrderItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponseDTO toDto(OrderItem orderItem);
    
    OrderItem toEntity(OrderItemRequestDTO orderItemRequestDTO);
}
