package ru.javacode.mvcobjecmapper.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javacode.mvcobjecmapper.order.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDtoShort {

    private Long orderId;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
}
