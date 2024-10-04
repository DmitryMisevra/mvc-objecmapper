package ru.javacode.mvcobjecmapper.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javacode.mvcobjecmapper.customer.model.Customer;
import ru.javacode.mvcobjecmapper.customer.repository.CustomerRepository;
import ru.javacode.mvcobjecmapper.exception.ResourceNotFoundException;
import ru.javacode.mvcobjecmapper.order.dto.CreateOrderDto;
import ru.javacode.mvcobjecmapper.order.dto.OrderDtoFull;
import ru.javacode.mvcobjecmapper.order.dto.OrderDtoShort;
import ru.javacode.mvcobjecmapper.order.dto.UpdateOrderDto;
import ru.javacode.mvcobjecmapper.order.mapper.OrderMapper;
import ru.javacode.mvcobjecmapper.order.model.Order;
import ru.javacode.mvcobjecmapper.order.repository.OrderRepository;
import ru.javacode.mvcobjecmapper.product.model.Product;
import ru.javacode.mvcobjecmapper.product.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDtoFull createOrder(Long customerId, CreateOrderDto createOrderDto) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new ResourceNotFoundException("Покупатель с id " + customerId + " не найден"));
        Order order = orderMapper.createsOrderDtoToOrder(createOrderDto);
        order.setProducts(convertProductIdsToProducts(createOrderDto.getProductIds()));
        order.setCustomer(customer);
        Order savedOrder = orderRepository.save(order);
        return getOrderById(savedOrder.getOrderId());
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDtoFull getOrderById(Long orderId) {
        Order order = orderRepository.findWithAllDetailsById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Заказ с id " + orderId + " не найден"));
        return orderMapper.orderToOrderDtoFull(order);
    }

    @Override
    public OrderDtoFull updateOrder(Long orderId, UpdateOrderDto updateOrderDto) {
        Order orderToUpdate = orderRepository.findWithAllDetailsById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Заказ с id " + orderId + " не найден"));
        if (updateOrderDto.getShippingAddress() != null) {
            orderToUpdate.setShippingAddress(updateOrderDto.getShippingAddress());
        }
        if (updateOrderDto.getOrderStatus() != null) {
            orderToUpdate.setOrderStatus(updateOrderDto.getOrderStatus());
        }
        List<Long> productIds = updateOrderDto.getProductIds();
        if (productIds != null && !productIds.isEmpty()) {
            List<Product> products = convertProductIdsToProducts(productIds);
            orderToUpdate.setProducts(products);
        }
        Order savedOrder = orderRepository.save(orderToUpdate);
        return getOrderById(savedOrder.getOrderId());
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDtoShort> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::orderToOrderDtoShort)
                .toList();
    }

    private List<Product> convertProductIdsToProducts(List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("Заказ не может быть пустым");
        }
        List<Product> products = productRepository.findByIdIn(productIds);
        if (products.size() != productIds.size()) {
            List<Long> foundProductIds = products.stream()
                    .map(Product::getProductId)
                    .toList();

            List<Long> missingProductIds = productIds.stream()
                    .filter(id -> !foundProductIds.contains(id))
                    .toList();

            throw new RuntimeException("Продукты с идентификаторами " + missingProductIds + " не найдены");
        }
        return products;
    }
}
