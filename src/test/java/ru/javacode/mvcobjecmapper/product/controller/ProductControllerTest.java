package ru.javacode.mvcobjecmapper.product.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.javacode.mvcobjecmapper.product.service.ProductService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void testCreateProduct() throws Exception {
        when(productService.createProduct(any(String.class))).thenReturn("Product Created");

        mockMvc.perform(post("/api/v1/products")
                        .contentType("application/json")
                        .content("{\"name\": \"Laptop\", \"price\": 1000}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateProduct() throws Exception {
        when(productService.updateProduct(eq(1L), any(String.class))).thenReturn("Product Updated");

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType("application/json")
                        .content("{\"name\": \"Laptop Pro\", \"price\": 1500}"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetProductById() throws Exception {
        when(productService.getProductById(eq(1L))).thenReturn("Product Details");

        mockMvc.perform(get("/api/v1/products/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(eq(1L));

        mockMvc.perform(delete("/api/v1/products/1")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetProducts() throws Exception {
        when(productService.getProducts()).thenReturn("All Products");

        mockMvc.perform(get("/api/v1/products")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}