package com.group05.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.group05.dto.OrderRequestDTO;
import com.group05.dto.OrderResponseDTO;
import com.group05.model.Order;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderItemMapper.class, OrderStateMapper.class})
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.email", target = "userEmail")
    OrderResponseDTO toDto(Order order);

    Order toEntity(OrderRequestDTO orderRequestDTO);

}
