package ru.javacode.mvcobjecmapper.order.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.javacode.mvcobjecmapper.customer.mapper.CustomerMapper;
import ru.javacode.mvcobjecmapper.order.dto.CreateOrderDto;
import ru.javacode.mvcobjecmapper.order.dto.OrderDtoFull;
import ru.javacode.mvcobjecmapper.order.dto.OrderDtoShort;
import ru.javacode.mvcobjecmapper.order.model.Order;
import ru.javacode.mvcobjecmapper.order.model.OrderStatus;
import ru.javacode.mvcobjecmapper.product.dto.ProductDto;
import ru.javacode.mvcobjecmapper.product.mapper.ProductMapper;
import ru.javacode.mvcobjecmapper.product.model.Product;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final CustomerMapper customerMapper;
    private final ProductMapper productMapper;


    public Order createsOrderDtoToOrder(CreateOrderDto createOrderDto) {
        return Order.builder()
                .shippingAddress(createOrderDto.getShippingAddress())
                .orderStatus(OrderStatus.ACCEPTED)
                .build();
    }

    public OrderDtoShort orderToOrderDtoShort(Order order) {
        return OrderDtoShort.builder()
                .orderId(order.getOrderId())
                .shippingAddress(order.getShippingAddress())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public OrderDtoFull orderToOrderDtoFull(Order order) {
        return OrderDtoFull.builder()
                .orderId(order.getOrderId())
                .customer(customerMapper.customerToCustomerDto(order.getCustomer()))
                .shippingAddress(order.getShippingAddress())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .products(convertProductsToProductDtos(order.getProducts()))
                .build();
    }

    private List<ProductDto> convertProductsToProductDtos(List<Product> products) {
        return products.stream().map(productMapper::productToProductDto).toList();
    }
}
