package ru.javacode.mvcobjecmapper.product.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.javacode.mvcobjecmapper.product.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductIdIn(@NotNull(message = "отсутствует список товаров в заказе") List<Long> productIds);
}