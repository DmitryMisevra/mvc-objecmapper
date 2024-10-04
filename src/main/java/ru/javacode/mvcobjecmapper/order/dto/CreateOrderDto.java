package ru.javacode.mvcobjecmapper.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateOrderDto {

    @NotBlank(message = "Не указан адрес доставки")
    private String shippingAddress;
    @NotNull(message = "отсутствует список товаров в заказе")
    private List<Long> productIds;
}
