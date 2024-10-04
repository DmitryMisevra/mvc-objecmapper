package ru.javacode.mvcobjecmapper.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import ru.javacode.mvcobjecmapper.order.dto.CreateOrderDto;
import ru.javacode.mvcobjecmapper.order.dto.OrderDtoFull;
import ru.javacode.mvcobjecmapper.order.model.OrderStatus;
import ru.javacode.mvcobjecmapper.product.dto.ProductDto;
import ru.javacode.mvcobjecmapper.customer.dto.CustomerDto;
import ru.javacode.mvcobjecmapper.customer.model.Customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonUtilsTest {

    @Test
    void testFromJson() {
        String json = "{" +
                "\"orderId\": 1," +
                "\"customer\": {\"customerId\": 123, \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"contactNumber\": \"1234567890\"}," +
                "\"orderDate\": \"2023-09-29 15:00:00\"," +
                "\"shippingAddress\": \"123 Elm Street\"," +
                "\"totalPrice\": 100.50," +
                "\"orderStatus\": \"ACCEPTED\"," +
                "\"products\": [{\"productId\": 1, \"name\": \"Laptop\", \"description\": \"High-end laptop\", \"price\": 1000, \"quantityInStock\": 10}]" +
                "}";

        OrderDtoFull order = JsonUtils.fromJson(json, OrderDtoFull.class);

        assertNotNull(order);
        assertEquals(1L, order.getOrderId());
        assertNotNull(order.getCustomer());
        assertEquals(123L, order.getCustomer().getCustomerId());
        assertEquals("John", order.getCustomer().getFirstName());
        assertEquals("Doe", order.getCustomer().getLastName());
        assertEquals("john.doe@example.com", order.getCustomer().getEmail());
        assertEquals("1234567890", order.getCustomer().getContactNumber());
        assertEquals("123 Elm Street", order.getShippingAddress());
        assertEquals(OrderStatus.ACCEPTED, order.getOrderStatus());
        assertEquals(1, order.getProducts().size());
        ProductDto product = order.getProducts().get(0);
        assertEquals(1L, product.getProductId());
        assertEquals("Laptop", product.getName());
        assertEquals("High-end laptop", product.getDescription());
        assertEquals(BigDecimal.valueOf(1000), product.getPrice());
        assertEquals(10, product.getQuantityInStock());
    }

    @Test
    void testToJson() throws JsonProcessingException {
        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .shippingAddress("456 Oak Avenue")
                .productIds(List.of(1L, 2L, 3L))
                .build();

        String json = JsonUtils.toJson(createOrderDto);

        assertNotNull(json);
        assertTrue(json.contains("\"shippingAddress\":\"456 Oak Avenue\""));
        assertTrue(json.contains("\"productIds\":[1,2,3]"));
    }

    @Test
    void testFromJsonInvalid() {
        String invalidJson = "{" +
                "\"orderId\": \"invalid_value\"" +
                "}";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> JsonUtils.fromJson(invalidJson, OrderDtoFull.class));
        assertTrue(exception.getMessage().contains("Ошибка десериализации JSON"));
    }

    @Test
    void testToJsonInvalid() {
        Object invalidObject = new Object() {
            // Invalid object without proper serialization
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> JsonUtils.toJson(invalidObject));
        assertTrue(exception.getMessage().contains("Ошибка сериализации объекта в JSON"));
    }
}