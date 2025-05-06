package com.group05.service.use_cases;

import java.util.List;

import com.group05.dto.OrderListResponseDTO;
import com.group05.dto.OrderRequestDTO;
import com.group05.dto.OrderResponseDTO;
import com.group05.dto.PaginationResponseDTO;

public interface OrderUseCase {

    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO updateOrder(OrderRequestDTO orderRequestDTO, Long orderId);
    PaginationResponseDTO<List<OrderListResponseDTO>> getOrdersByUser(Long userId, Long pageNumber, Long pageSize);
    OrderResponseDTO getOrderDetail(Long orderId);
    void deleteOrder(Long orderId);
    OrderListResponseDTO cancelOrder(Long orderId);
}
