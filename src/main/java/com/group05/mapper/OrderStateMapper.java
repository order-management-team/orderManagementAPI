package com.group05.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.group05.dto.OrderStateResponseDTO;
import com.group05.model.OrderState;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderStateMapper {
    OrderStateResponseDTO toDto(OrderState orderState);
}
