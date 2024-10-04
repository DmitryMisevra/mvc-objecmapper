package ru.javacode.mvcobjecmapper.order.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.javacode.mvcobjecmapper.customer.model.Customer;
import ru.javacode.mvcobjecmapper.customer.repository.CustomerRepository;
import ru.javacode.mvcobjecmapper.exception.ResourceNotFoundException;
import ru.javacode.mvcobjecmapper.order.dto.CreateOrderDto;
import ru.javacode.mvcobjecmapper.order.dto.OrderDtoFull;
import ru.javacode.mvcobjecmapper.order.dto.UpdateOrderDto;
import ru.javacode.mvcobjecmapper.order.model.Order;
import ru.javacode.mvcobjecmapper.order.model.OrderStatus;
import ru.javacode.mvcobjecmapper.order.repository.OrderRepository;
import ru.javacode.mvcobjecmapper.product.model.Product;
import ru.javacode.mvcobjecmapper.product.repository.ProductRepository;
import ru.javacode.mvcobjecmapper.util.JsonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testCreateOrder() {
        // Arrange
        Customer customer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .contactNumber("+79123456789")
                .build();
        customer = customerRepository.save(customer);

        Product product1 = Product.builder()
                .name("Laptop")
                .description("High-end gaming laptop")
                .price(BigDecimal.valueOf(1500))
                .quantityInStock(10)
                .build();
        Product product2 = Product.builder()
                .name("Mouse")
                .description("Wireless mouse")
                .price(BigDecimal.valueOf(50))
                .quantityInStock(20)
                .build();
        product1 = productRepository.save(product1);
        product2 = productRepository.save(product2);

        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .shippingAddress("123 Elm Street")
                .productIds(List.of(product1.getProductId(), product2.getProductId()))
                .build();
        String createOrderJson = JsonUtils.toJson(createOrderDto);

        // Act
        String responseJson = orderService.createOrder(customer.getCustomerId(), createOrderJson);
        OrderDtoFull orderDtoFull = JsonUtils.fromJson(responseJson, OrderDtoFull.class);

        // Assert
        Optional<Order> savedOrder = orderRepository.findById(orderDtoFull.getOrderId());
        assertThat(savedOrder).isPresent();
        assertThat(savedOrder.get().getCustomer()).isEqualTo(customer);
        assertThat(savedOrder.get().getProducts()).hasSize(2);
        assertThat(savedOrder.get().getShippingAddress()).isEqualTo("123 Elm Street");
        assertThat(savedOrder.get().getOrderStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Test
    void testUpdateOrder() {
        // Arrange
        Customer customer = Customer.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .contactNumber("+79098765432")
                .build();
        customer = customerRepository.save(customer);

        Product product = Product.builder()
                .name("Tablet")
                .description("Basic tablet")
                .price(BigDecimal.valueOf(300))
                .quantityInStock(15)
                .build();
        product = productRepository.save(product);

        Order order = Order.builder()
                .customer(customer)
                .shippingAddress("456 Oak Avenue")
                .orderStatus(OrderStatus.ACCEPTED)
                .products(new ArrayList<>(List.of(product)))
                .build();
        order = orderRepository.save(order);

        UpdateOrderDto updateOrderDto = UpdateOrderDto.builder()
                .shippingAddress("789 Maple Street")
                .build();
        String updateOrderJson = JsonUtils.toJson(updateOrderDto);

        // Act
        String responseJson = orderService.updateOrder(order.getOrderId(), updateOrderJson);
        OrderDtoFull updatedOrderDto = JsonUtils.fromJson(responseJson, OrderDtoFull.class);

        // Assert
        assertThat(updatedOrderDto.getShippingAddress()).isEqualTo("789 Maple Street");
    }

    @Test
    void testGetOrderById() {
        // Arrange
        Customer customer = Customer.builder()
                .firstName("Alice")
                .lastName("Smith")
                .email("alice.smith@example.com")
                .contactNumber("+79112233445")
                .build();
        customer = customerRepository.save(customer);

        Product product = Product.builder()
                .name("Smartphone")
                .description("Latest model smartphone")
                .price(BigDecimal.valueOf(1000))
                .quantityInStock(5)
                .build();
        product = productRepository.save(product);

        Order order = Order.builder()
                .customer(customer)
                .shippingAddress("123 Pine Street")
                .orderStatus(OrderStatus.ACCEPTED)
                .products(new ArrayList<>(List.of(product)))
                .build();
        order = orderRepository.save(order);

        // Act
        String responseJson = orderService.getOrderById(order.getOrderId());
        OrderDtoFull orderDtoFull = JsonUtils.fromJson(responseJson, OrderDtoFull.class);

        // Assert
        assertThat(orderDtoFull.getOrderId()).isEqualTo(order.getOrderId());
        assertThat(orderDtoFull.getShippingAddress()).isEqualTo("123 Pine Street");
    }

    @Test
    void testDeleteOrder() {
        // Arrange
        Customer customer = Customer.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .email("bob.johnson@example.com")
                .contactNumber("+79223344556")
                .build();
        customer = customerRepository.save(customer);

        Product product = Product.builder()
                .name("Headphones")
                .description("Wireless headphones")
                .price(BigDecimal.valueOf(200))
                .quantityInStock(10)
                .build();
        product = productRepository.save(product);

        Order order = Order.builder()
                .customer(customer)
                .shippingAddress("456 Birch Avenue")
                .orderStatus(OrderStatus.ACCEPTED)
                .products(new ArrayList<>(List.of(product)))
                .build();
        order = orderRepository.save(order);

        // Act
        orderService.deleteOrder(order.getOrderId());

        // Assert
        Optional<Order> deletedOrder = orderRepository.findById(order.getOrderId());
        assertThat(deletedOrder).isNotPresent();
    }

    @Test
    void testGetOrderById_NotFound() {
        // Act & Assert
        assertThatThrownBy(() -> orderService.getOrderById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Заказ с id 999 не найден");
    }
}