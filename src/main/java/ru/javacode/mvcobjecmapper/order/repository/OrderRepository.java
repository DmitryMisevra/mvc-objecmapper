package ru.javacode.mvcobjecmapper.order.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.javacode.mvcobjecmapper.order.model.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"customer", "products"})
    Optional<Order> findWithAllDetailsById(Long id);
}
