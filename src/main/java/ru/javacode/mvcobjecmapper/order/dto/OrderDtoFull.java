package ru.javacode.mvcobjecmapper.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javacode.mvcobjecmapper.customer.dto.CustomerDto;
import ru.javacode.mvcobjecmapper.order.model.OrderStatus;
import ru.javacode.mvcobjecmapper.product.dto.ProductDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDtoFull {

    private Long orderId;
    private CustomerDto customer;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private List<ProductDto> products;
}
