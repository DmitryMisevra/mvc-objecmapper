package ru.javacode.mvcobjecmapper.customer.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.javacode.mvcobjecmapper.customer.service.CustomerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void testCreateCustomer() throws Exception {
        when(customerService.createCustomer(any(String.class))).thenReturn("Customer Created");

        mockMvc.perform(post("/api/v1/customers")
                        .contentType("application/json")
                        .content("{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        when(customerService.updateCustomer(eq(1L), any(String.class))).thenReturn("Customer Updated");

        mockMvc.perform(put("/api/v1/customers/1")
                        .contentType("application/json")
                        .content("{\"name\": \"Jane Doe\", \"email\": \"jane.doe@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCustomerById() throws Exception {
        when(customerService.getCustomerById(eq(1L))).thenReturn("Customer Details");

        mockMvc.perform(get("/api/v1/customers/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCustomerById() throws Exception {
        doNothing().when(customerService).deleteCustomerById(eq(1L));

        mockMvc.perform(delete("/api/v1/customers/1")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn("All Customers");

        mockMvc.perform(get("/api/v1/customers")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}