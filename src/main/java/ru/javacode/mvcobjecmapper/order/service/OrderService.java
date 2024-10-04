package ru.javacode.mvcobjecmapper.order.service;

public interface OrderService {

    String createOrder(Long customerId, String createOrderJson);

    String getOrderById(Long orderId);

    String updateOrder(Long orderId, String updateOrderJson);

    void deleteOrder(Long orderId);

    String getAllOrders();
}
