package ru.javacode.mvcobjecmapper.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "product_name", nullable = false)
    private String name;
    @Column(name = "product_description", nullable = false)
    private String description;
    @Column(name = "product_price", nullable = false)
    private BigDecimal price;
    @Column(name = "quantity", nullable = false)
    private Integer quantityInStock;
}
