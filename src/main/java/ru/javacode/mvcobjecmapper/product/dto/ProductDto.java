package ru.javacode.mvcobjecmapper.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDto {

    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityInStock;
}
