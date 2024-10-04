package ru.javacode.mvcobjecmapper.product.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.javacode.mvcobjecmapper.exception.ResourceNotFoundException;
import ru.javacode.mvcobjecmapper.product.dto.CreateProductDto;
import ru.javacode.mvcobjecmapper.product.dto.ProductDto;
import ru.javacode.mvcobjecmapper.product.dto.UpdateProductDto;
import ru.javacode.mvcobjecmapper.product.model.Product;
import ru.javacode.mvcobjecmapper.product.repository.ProductRepository;
import ru.javacode.mvcobjecmapper.util.JsonUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testCreateProduct() {
        // Arrange
        CreateProductDto createProductDto = CreateProductDto.builder()
                .name("Laptop")
                .description("High-end gaming laptop")
                .price(BigDecimal.valueOf(1500))
                .quantityInStock(10)
                .build();
        String createProductJson = JsonUtils.toJson(createProductDto);

        // Act
        String responseJson = productService.createProduct(createProductJson);
        ProductDto productDto = JsonUtils.fromJson(responseJson, ProductDto.class);

        // Assert
        Optional<Product> savedProduct = productRepository.findById(productDto.getProductId());
        assertThat(savedProduct).isPresent();
        assertThat(savedProduct.get().getName()).isEqualTo("Laptop");
        assertThat(savedProduct.get().getDescription()).isEqualTo("High-end gaming laptop");
        assertThat(savedProduct.get().getPrice()).isEqualByComparingTo(BigDecimal.valueOf(1500));
        assertThat(savedProduct.get().getQuantityInStock()).isEqualTo(10);
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        Product product = Product.builder()
                .name("Tablet")
                .description("Basic tablet")
                .price(BigDecimal.valueOf(300))
                .quantityInStock(20)
                .build();
        product = productRepository.save(product);

        UpdateProductDto updateProductDto = UpdateProductDto.builder()
                .name("Advanced Tablet")
                .price(BigDecimal.valueOf(400))
                .build();
        String updateProductJson = JsonUtils.toJson(updateProductDto);

        // Act
        String responseJson = productService.updateProduct(product.getProductId(), updateProductJson);
        ProductDto updatedProductDto = JsonUtils.fromJson(responseJson, ProductDto.class);

        // Assert
        Optional<Product> updatedProduct = productRepository.findById(updatedProductDto.getProductId());
        assertThat(updatedProduct).isPresent();
        assertThat(updatedProduct.get().getName()).isEqualTo("Advanced Tablet");
        assertThat(updatedProduct.get().getDescription()).isEqualTo("Basic tablet");
        assertThat(updatedProduct.get().getPrice()).isEqualByComparingTo(BigDecimal.valueOf(400));
        assertThat(updatedProduct.get().getQuantityInStock()).isEqualTo(20);
    }

    @Test
    void testGetProductById() {
        // Arrange
        Product product = Product.builder()
                .name("Smartphone")
                .description("Latest model smartphone")
                .price(BigDecimal.valueOf(1000))
                .quantityInStock(15)
                .build();
        product = productRepository.save(product);

        // Act
        String responseJson = productService.getProductById(product.getProductId());
        ProductDto productDto = JsonUtils.fromJson(responseJson, ProductDto.class);

        // Assert
        assertThat(productDto.getProductId()).isEqualTo(product.getProductId());
        assertThat(productDto.getName()).isEqualTo("Smartphone");
        assertThat(productDto.getDescription()).isEqualTo("Latest model smartphone");
        assertThat(productDto.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(1000));
        assertThat(productDto.getQuantityInStock()).isEqualTo(15);
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Product product = Product.builder()
                .name("Headphones")
                .description("Wireless headphones")
                .price(BigDecimal.valueOf(200))
                .quantityInStock(30)
                .build();
        product = productRepository.save(product);

        // Act
        productService.deleteProduct(product.getProductId());

        // Assert
        Optional<Product> deletedProduct = productRepository.findById(product.getProductId());
        assertThat(deletedProduct).isNotPresent();
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        Product product1 = Product.builder()
                .name("Monitor")
                .description("4K monitor")
                .price(BigDecimal.valueOf(500))
                .quantityInStock(8)
                .build();
        Product product2 = Product.builder()
                .name("Keyboard")
                .description("Mechanical keyboard")
                .price(BigDecimal.valueOf(100))
                .quantityInStock(25)
                .build();
        productRepository.save(product1);
        productRepository.save(product2);

        // Act
        String responseJson = productService.getProducts();
        List<ProductDto> products = JsonUtils.fromJson(responseJson, List.class);

        // Assert
        assertThat(products).hasSize(2);
    }

    @Test
    void testGetProductById_NotFound() {
        // Act & Assert
        assertThatThrownBy(() -> productService.getProductById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Товар с id 999 не найден");
    }
}