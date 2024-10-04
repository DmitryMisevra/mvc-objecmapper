package ru.javacode.mvcobjecmapper.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateProductDto {

    @NotBlank(message = "Не указано имя товара")
    private String name;
    @NotBlank(message = "Не указано описание товара")
    @Size(max = 200, message = "Размер описания не может превышать 200 символов")
    private String description;
    @NotNull(message = "не указана стоимость товара")
    private BigDecimal price;
    @NotNull(message = "не указано количество товара на складе")
    @PositiveOrZero(message = "количество на складе не может быть отрицательным числом")
    private Integer quantityInStock;
}
