package ru.javacode.mvcobjecmapper.order.service;

import jakarta.validation.Validator;
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
import ru.javacode.mvcobjecmapper.util.JsonUtils;
import ru.javacode.mvcobjecmapper.util.ValidationUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final Validator validator;

    @Override
    public String createOrder(Long customerId, String createOrderJson) {
        CreateOrderDto createOrderDto = JsonUtils.fromJson(createOrderJson, CreateOrderDto.class);
        ValidationUtils.validate(validator, createOrderDto);
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
    public String getOrderById(Long orderId) {
        Order order = orderRepository.findWithAllDetailsByOrderId(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Заказ с id " + orderId + " не найден"));
        order.setTotalPrice(calculateTotalPrice(order.getProducts()));
        OrderDtoFull orderDtoFull = orderMapper.orderToOrderDtoFull(order);
        return JsonUtils.toJson(orderDtoFull);
    }

    @Override
    public String updateOrder(Long orderId, String updateOrderJson) {
        UpdateOrderDto updateOrderDto = JsonUtils.fromJson(updateOrderJson, UpdateOrderDto.class);
        ValidationUtils.validate(validator, updateOrderDto);
        Order orderToUpdate = orderRepository.findWithAllDetailsByOrderId(orderId).orElseThrow(() ->
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
    public String getAllOrders() {
        List<OrderDtoShort> orderDtoShorts = orderRepository.findAll().stream()
                .map(orderMapper::orderToOrderDtoShort)
                .toList();
        return JsonUtils.toJson(orderDtoShorts);
    }

    private List<Product> convertProductIdsToProducts(List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("Заказ не может быть пустым");
        }
        List<Product> products = productRepository.findByProductIdIn(productIds);
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

    private BigDecimal calculateTotalPrice(List<Product> products) {
        return products.stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantityInStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
