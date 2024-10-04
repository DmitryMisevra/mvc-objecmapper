package ru.javacode.mvcobjecmapper.product.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateProductDto {

    private String name;
    @Size(max = 200, message = "Размер описания не может превышать 200 символов")
    private String description;
    @PositiveOrZero(message = "стоимость товара не может быть отрицательной")
    private BigDecimal price;
    @PositiveOrZero(message = "количество на складе не может быть отрицательным числом")
    private Integer quantityInStock;
}
