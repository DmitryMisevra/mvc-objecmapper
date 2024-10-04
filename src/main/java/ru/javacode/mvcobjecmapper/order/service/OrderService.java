package ru.javacode.mvcobjecmapper.order.service;

import ru.javacode.mvcobjecmapper.order.dto.CreateOrderDto;
import ru.javacode.mvcobjecmapper.order.dto.OrderDtoFull;
import ru.javacode.mvcobjecmapper.order.dto.OrderDtoShort;
import ru.javacode.mvcobjecmapper.order.dto.UpdateOrderDto;

import java.util.List;

public interface OrderService {

    OrderDtoFull createOrder(Long customerId, CreateOrderDto createOrderDto);

    OrderDtoFull getOrderById(Long orderId);

    OrderDtoFull updateOrder(Long orderId, UpdateOrderDto updateOrderDto);

    void deleteOrder(Long orderId);

    List<OrderDtoShort> getAllOrders();
}
