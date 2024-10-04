package ru.javacode.mvcobjecmapper.order.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javacode.mvcobjecmapper.customer.model.Customer;
import ru.javacode.mvcobjecmapper.customer.repository.CustomerRepository;
import ru.javacode.mvcobjecmapper.order.model.Order;
import ru.javacode.mvcobjecmapper.order.model.OrderStatus;
import ru.javacode.mvcobjecmapper.product.model.Product;
import ru.javacode.mvcobjecmapper.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindWithAllDetailsByOrderId() {
        // Arrange
        Customer customer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .contactNumber("1234567890")
                .build();
        customer = customerRepository.save(customer);

        Product product1 = Product.builder()
                .name("Laptop")
                .description("High-end gaming laptop")
                .price(BigDecimal.valueOf(1500))
                .quantityInStock(5)
                .build();
        Product product2 = Product.builder()
                .name("Mouse")
                .description("Wireless mouse")
                .price(BigDecimal.valueOf(50))
                .quantityInStock(20)
                .build();
        product1 = productRepository.save(product1);
        product2 = productRepository.save(product2);

        Order order = Order.builder()
                .customer(customer)
                .orderDate(LocalDateTime.now())
                .shippingAddress("123 Elm Street")
                .totalPrice(BigDecimal.valueOf(1550))
                .orderStatus(OrderStatus.ACCEPTED)
                .products(List.of(product1, product2))
                .build();
        orderRepository.save(order);

        // Act
        Optional<Order> retrievedOrder = orderRepository.findWithAllDetailsByOrderId(order.getOrderId());

        // Assert
        assertThat(retrievedOrder).isPresent();
        assertThat(retrievedOrder.get().getCustomer()).isEqualTo(customer);
        assertThat(retrievedOrder.get().getProducts()).hasSize(2);
        assertThat(retrievedOrder.get().getProducts()).extracting(Product::getName).containsExactlyInAnyOrder("Laptop", "Mouse");
        assertThat(retrievedOrder.get().getShippingAddress()).isEqualTo("123 Elm Street");
        assertThat(retrievedOrder.get().getTotalPrice()).isEqualByComparingTo(BigDecimal.valueOf(1550));
        assertThat(retrievedOrder.get().getOrderStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }
}