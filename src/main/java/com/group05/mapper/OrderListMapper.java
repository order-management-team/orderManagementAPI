package com.group05.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.group05.dto.OrderListResponseDTO;
import com.group05.model.Order;

@Mapper(componentModel = "spring",uses = {OrderStateMapper.class} , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderListMapper {
    OrderListResponseDTO toListDto(Order order);
}
