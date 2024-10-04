package ru.javacode.mvcobjecmapper.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javacode.mvcobjecmapper.order.model.OrderStatus;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateOrderDto {

    private String shippingAddress;
    private OrderStatus orderStatus;
    private List<Long> productIds;
}
