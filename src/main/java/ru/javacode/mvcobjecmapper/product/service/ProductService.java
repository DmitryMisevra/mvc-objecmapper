package ru.javacode.mvcobjecmapper.product.service;

public interface ProductService {

    String createProduct(String createProductJson);

    String updateProduct(Long productId, String updateProductJson);

    String getProductById(Long productId);

    void deleteProduct(Long productId);

    String getProducts();
}
