package ru.javacode.mvcobjecmapper.order.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.javacode.mvcobjecmapper.order.service.OrderService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void testCreateOrder() throws Exception {
        when(orderService.createOrder(eq(1L), any(String.class))).thenReturn("Order Created");

        mockMvc.perform(post("/api/v1/orders/1")
                        .contentType("application/json")
                        .content("{\"product\": \"Book\", \"quantity\": 2}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateOrder() throws Exception {
        when(orderService.updateOrder(eq(1L), any(String.class))).thenReturn("Order Updated");

        mockMvc.perform(put("/api/v1/orders/1")
                        .contentType("application/json")
                        .content("{\"product\": \"Pen\", \"quantity\": 5}"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOrderById() throws Exception {
        when(orderService.getOrderById(eq(1L))).thenReturn("Order Details");

        mockMvc.perform(get("/api/v1/orders/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(eq(1L));

        mockMvc.perform(delete("/api/v1/orders/1")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn("All Orders");

        mockMvc.perform(get("/api/v1/orders")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}