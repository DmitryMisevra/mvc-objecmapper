package ru.javacode.mvcobjecmapper.order.controller;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javacode.mvcobjecmapper.order.service.OrderService;

@RestController
@RequestMapping(path = "/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(path = "/{customerId}", produces = "application/json")
    ResponseEntity<String> createOrder(@PathVariable @Min(1) Long customerId,
                                       @RequestBody String createOrderJson) {
        return new ResponseEntity<>(orderService.createOrder(customerId, createOrderJson), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{orderId}", produces = "application/json")
    ResponseEntity<String> updateOrder(@PathVariable @Min(1) Long orderId,
                                       @RequestBody String updateOrderJson) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, updateOrderJson));
    }

    @GetMapping(path = "/{orderId}", produces = "application/json")
    ResponseEntity<String> getOrderById(@PathVariable @Min(1) Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @DeleteMapping(path = "/{orderId}")
    ResponseEntity<Void> deleteOrder(@PathVariable @Min(1) Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json")
    ResponseEntity<String> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
