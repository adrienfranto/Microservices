package com.techie.microservices.order_service.service;

import com.techie.microservices.order_service.client.InventoryClient;
import com.techie.microservices.order_service.dto.OrderDto;
import com.techie.microservices.order_service.dto.OrderRequest;
import com.techie.microservices.order_service.model.Order;
import com.techie.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest) {

        boolean isProductInStock = inventoryClient
                .isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(orderRequest.orderNumber());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            orderRepository.save(order);
        } else {
            throw new RuntimeException(
                    "Product with SkuCode " + orderRequest.skuCode() + " is not in stock"
            );
        }
    }

    // READ - all orders
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // READ - by id
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        return mapToDto(order);
    }

    // UPDATE
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        existingOrder.setOrderNumber(orderDto.getOrderNumber());
        existingOrder.setSkuCode(orderDto.getSkuCode());
        existingOrder.setPrice(orderDto.getPrice());
        existingOrder.setQuantity(orderDto.getQuantity());

        return mapToDto(orderRepository.save(existingOrder));
    }

    // DELETE
    public void deleteOrder(Long id) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        orderRepository.delete(existingOrder);
    }

    // Mapping helper
    private OrderDto mapToDto(Order order){
        return new OrderDto(
                order.getId(),
                order.getOrderNumber(),
                order.getSkuCode(),
                order.getPrice(),
                order.getQuantity()
        );
    }

    private Order mapToEntity(OrderDto orderDto){
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setOrderNumber(orderDto.getOrderNumber());
        order.setSkuCode(orderDto.getSkuCode());
        order.setPrice(orderDto.getPrice());
        order.setQuantity(orderDto.getQuantity());
        return order;
    }


}
