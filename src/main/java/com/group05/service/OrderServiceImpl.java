package com.group05.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.group05.constant.ExceptionMessages;
import com.group05.constant.OrderConstants;
import com.group05.constant.RecordStateConstants;
import com.group05.dto.OrderItemRequestDTO;
import com.group05.dto.OrderItemResponseDTO;
import com.group05.dto.OrderListResponseDTO;
import com.group05.dto.OrderRequestDTO;
import com.group05.dto.OrderResponseDTO;
import com.group05.dto.OrderStateResponseDTO;
import com.group05.dto.PageResponseDTO;
import com.group05.dto.PaginationResponseDTO;
import com.group05.exceptionHandler.exceptions.InsufficientStockException;
import com.group05.exceptionHandler.exceptions.ResourceNotFoundException;
import com.group05.mapper.OrderItemMapper;
import com.group05.mapper.OrderListMapper;
import com.group05.mapper.OrderMapper;
import com.group05.model.Order;
import com.group05.model.OrderItem;
import com.group05.model.OrderState;
import com.group05.model.Product;
import com.group05.model.ProductPrice;
import com.group05.model.User;
import com.group05.repository.OrderItemRepository;
import com.group05.repository.OrderRepository;
import com.group05.repository.OrderStateRepository;
import com.group05.repository.ProductRepository;
import com.group05.repository.UserRepository;
import com.group05.service.use_cases.OrderUseCase;
import com.group05.service.use_cases.ProductPriceUseCase;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderUseCase {

    private UserRepository userRepository;

    private OrderStateRepository orderStateRepository;

    private ProductRepository productRepository;

    private OrderRepository orderRepository;

    private OrderItemRepository orderItemRepository;

    private ProductPriceUseCase productPriceUseCase;

    private OrderMapper orderMapper;

    private OrderListMapper orderListMapper;

    private OrderItemMapper orderItemMapper;


    public OrderServiceImpl(UserRepository userRepository, OrderStateRepository orderStateRepository,
            ProductRepository productRepository, OrderRepository orderRepository, OrderMapper orderMapper,
            OrderItemRepository orderItemRepository, ProductPriceUseCase productPriceUseCase, OrderListMapper orderListMapper,
            OrderItemMapper orderItemMapper) {
        this.userRepository = userRepository;
        this.orderStateRepository = orderStateRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemRepository = orderItemRepository;
        this.productPriceUseCase = productPriceUseCase;
        this.orderListMapper = orderListMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        User user = userRepository.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(ExceptionMessages.USER_ID_NOT_FOUND, orderRequestDTO.getUserId())));

        OrderState state = orderStateRepository.findById(OrderConstants.STATE_ID_CREATED)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ExceptionMessages.STATE_NOT_FOUND, OrderConstants.STATE_ID_CREATED)));

        Order order = new Order();
        order.setUser(user);
        order.setState(state);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDTO itemDto : orderRequestDTO.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format(ExceptionMessages.PRODUCT_NOT_FOUND, itemDto.getProductId())));

            int requestedQuantity = itemDto.getQuantity();

            if (requestedQuantity > product.getStock()) {
                throw new InsufficientStockException(
                        String.format(ExceptionMessages.INSUFFICIENT_STOCK, product.getId(), product.getStock(),
                                requestedQuantity));
            }

            product.setStock(product.getStock() - requestedQuantity);

            ProductPrice activePrice = productPriceUseCase.getActivePrice(product);
            if (activePrice == null) {
                throw new ResourceNotFoundException(String.format(ExceptionMessages.PRODUCT_PRICE_NOT_FOUND, product.getName()));
            }

            BigDecimal subtotal = activePrice.getPrice().multiply(BigDecimal.valueOf(requestedQuantity));
            totalAmount = totalAmount.add(subtotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setProductPrice(activePrice);
            orderItem.setOrder(order);
            orderItem.setQuantity(requestedQuantity);
            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        user.setTotalSpent(user.getTotalSpent().add(totalAmount));

        Order orderSaved = orderRepository.save(order);

        // Construcción manual del DTO
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setId(orderSaved.getId());
        responseDTO.setDate(orderSaved.getDate());
        responseDTO.setUserId(user.getId());
        responseDTO.setUserName(user.getName());
        responseDTO.setUserEmail(user.getEmail());
        responseDTO.setTotalAmount(orderSaved.getTotalAmount());
        responseDTO.setState(new OrderStateResponseDTO(state.getId(), state.getName()));

        // Mapear ítems
        List<OrderItemResponseDTO> itemDTOs = orderSaved.getItems().stream().map(item -> {
            OrderItemResponseDTO dto = new OrderItemResponseDTO();
            dto.setId(item.getId());
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            dto.setUnitPrice(item.getProductPrice().getPrice());
            dto.setSubtotal(item.getSubtotal());
            return dto;
        }).toList();

        responseDTO.setItems(itemDTOs);

        return responseDTO;
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrder(OrderRequestDTO orderRequestDTO, Long orderId) {
        // Obtener usuario
        User user = userRepository.findById(orderRequestDTO.getUserId())
            .orElseThrow(() -> new UsernameNotFoundException(
                String.format(ExceptionMessages.USER_ID_NOT_FOUND, orderRequestDTO.getUserId())));

        // Obtener estado "CREADO"
        OrderState state = orderStateRepository.findById(OrderConstants.STATE_ID_CREATED)
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format(ExceptionMessages.STATE_NOT_FOUND, OrderConstants.STATE_ID_CREATED)));

        // Obtener orden existente
        Order existingOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format(ExceptionMessages.ORDER_NOT_FOUND, orderId)));

        // Total anterior 
        BigDecimal previousTotal = existingOrder.getTotalAmount() != null ? existingOrder.getTotalAmount() : BigDecimal.ZERO;

        // Ítems actuales activos
        List<OrderItem> activeItems = orderItemRepository.findByOrderIdAndFlgState(orderId, RecordStateConstants.ACTIVE);
        Map<Long, OrderItem> existingItemsMap = new HashMap<>();
        for (OrderItem item : activeItems) {
            existingItemsMap.put(item.getId(), item);
        }

        List<OrderItem> finalItems = new ArrayList<>();

        for (OrderItemRequestDTO itemDTO : orderRequestDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    String.format(ExceptionMessages.PRODUCT_NOT_FOUND, itemDTO.getProductId())));

            OrderItem item;

            if (itemDTO.getId() != null && existingItemsMap.containsKey(itemDTO.getId())) {
                // Actualizar ítem existente
                item = existingItemsMap.get(itemDTO.getId());

                int originalQty = item.getQuantity();
                int newQty = itemDTO.getQuantity();
                int diff = newQty - originalQty;

                if (diff > 0 && diff > product.getStock()) {
                    throw new InsufficientStockException(String.format(
                        ExceptionMessages.INSUFFICIENT_STOCK, product.getId(), product.getStock(), diff));
                }

                product.setStock(product.getStock() - diff);
                item.setQuantity(newQty);

            } else {
                // Crear nuevo ítem
                if (itemDTO.getQuantity() > product.getStock()) {
                    throw new InsufficientStockException(String.format(
                        ExceptionMessages.INSUFFICIENT_STOCK, product.getId(), product.getStock(), itemDTO.getQuantity()));
                }

                product.setStock(product.getStock() - itemDTO.getQuantity());

                ProductPrice price = productPriceUseCase.getActivePrice(product);

                item = new OrderItem();
                item.setOrder(existingOrder);
                item.setProduct(product);
                item.setProductPrice(price);
                item.setQuantity(itemDTO.getQuantity());
                item.setFlgState(RecordStateConstants.ACTIVE);
            }

            // Calcular y setear subtotal actualizado
            BigDecimal unitPrice = item.getProductPrice().getPrice();
            item.setSubtotal(unitPrice.multiply(BigDecimal.valueOf(item.getQuantity())));

            finalItems.add(item);
            existingItemsMap.remove(itemDTO.getId());
        }

        // Marcar ítems eliminados como inactivos
        for (OrderItem toDisable : existingItemsMap.values()) {
            toDisable.setFlgState(RecordStateConstants.INACTIVE);
            finalItems.add(toDisable);
        }

        // Calcular nuevo total
        BigDecimal newTotal = finalItems.stream()
            .filter(item -> RecordStateConstants.ACTIVE.equals(item.getFlgState()))
            .map(OrderItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);  

        // Actualizar entidad Order
        existingOrder.setUser(user);
        existingOrder.setState(state);
        existingOrder.setItems(finalItems);
        existingOrder.setTotalAmount(newTotal);

        // Actualizar totalSpent del usuario
        BigDecimal currentSpent = user.getTotalSpent() != null ? user.getTotalSpent() : BigDecimal.ZERO;
        user.setTotalSpent(currentSpent.subtract(previousTotal).add(newTotal));

        // Guardar
        Order savedOrder = orderRepository.save(existingOrder);

        // Devolver DTO
        return orderMapper.toDto(savedOrder);
    }


    @Override
    public PaginationResponseDTO<List<OrderListResponseDTO>> getOrdersByUser(Long userId, Long pageNumber, Long pageSize) {
        Pageable pageable = PageRequest.of(Math.toIntExact(pageNumber), Math.toIntExact(pageSize));
        Page<Order> ordersPage = orderRepository.findByUserIdAndFlgStateAndUser_FlgState(
                pageable,
                userId,
                RecordStateConstants.ACTIVE,
                RecordStateConstants.ACTIVE);
        List<OrderListResponseDTO> ordersDTO = ordersPage.getContent().stream().map(orderListMapper::toListDto).toList();

        PageResponseDTO pageResponseDTO = new PageResponseDTO();
        pageResponseDTO.setNumber((long) ordersPage.getNumber());
        pageResponseDTO.setSize((long) ordersPage.getSize());
        pageResponseDTO.setTotalElements((long) ordersPage.getTotalElements());
        pageResponseDTO.setTotalPages((long) ordersPage.getTotalPages());

        PaginationResponseDTO<List<OrderListResponseDTO>> paginationResponseDTO = new PaginationResponseDTO<>();
        paginationResponseDTO.setContent(ordersDTO);
        paginationResponseDTO.setPage(pageResponseDTO);

        return paginationResponseDTO;

    }

    @Override
    public OrderResponseDTO getOrderDetail(Long orderId) {
        Order order = orderRepository.findByIdAndFlgState(orderId, RecordStateConstants.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format(ExceptionMessages.ORDER_NOT_FOUND, orderId)));

        OrderResponseDTO responseDTO = orderMapper.toDto(order);

        List<OrderItemResponseDTO> itemsWithPriceDTO = order.getItems().stream()
            .map(item -> {
                OrderItemResponseDTO dto = orderItemMapper.toDto(item);
                dto.setUnitPrice(item.getProductPrice().getPrice()); 
                return dto;
            })
            .toList();

        responseDTO.setItems(itemsWithPriceDTO);

        return responseDTO;
    }


    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ExceptionMessages.ORDER_NOT_FOUND, orderId)));

        order.setFlgState(RecordStateConstants.INACTIVE);
        
        for(OrderItem item : order.getItems()){
            item.setFlgState(RecordStateConstants.INACTIVE);
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
        }

        orderRepository.save(order);
    }

    @Override
    public OrderListResponseDTO cancelOrder(Long orderId) {
        Order order = orderRepository.findByIdAndFlgState(orderId, RecordStateConstants.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ExceptionMessages.ORDER_NOT_FOUND, orderId)));

        OrderState canceledState = orderStateRepository.findById(OrderConstants.STATE_ID_CANCELED)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ExceptionMessages.STATE_NOT_FOUND, OrderConstants.STATE_ID_CANCELED)));

        order.setState(canceledState);

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
        }

        Order savedOrder = orderRepository.save(order);

        return orderListMapper.toListDto(savedOrder);
    }

}
