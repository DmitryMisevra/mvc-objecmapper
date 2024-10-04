package ru.javacode.mvcobjecmapper.product.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javacode.mvcobjecmapper.product.model.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindByProductIdIn() {
        // Arrange
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

        productRepository.save(product1);
        productRepository.save(product2);

        List<Long> productIds = List.of(product1.getProductId(), product2.getProductId());

        // Act
        List<Product> products = productRepository.findByProductIdIn(productIds);

        // Assert
        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getName).containsExactlyInAnyOrder("Laptop", "Mouse");
        assertThat(products).extracting(Product::getDescription).containsExactlyInAnyOrder("High-end gaming laptop", "Wireless mouse");
        assertThat(products).extracting(Product::getPrice).containsExactlyInAnyOrder(BigDecimal.valueOf(1500), BigDecimal.valueOf(50));
        assertThat(products).extracting(Product::getQuantityInStock).containsExactlyInAnyOrder(5, 20);
    }
}