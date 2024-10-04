package ru.javacode.mvcobjecmapper.product.service;

import ru.javacode.mvcobjecmapper.product.dto.CreateProductDto;
import ru.javacode.mvcobjecmapper.product.dto.ProductDto;
import ru.javacode.mvcobjecmapper.product.dto.UpdateProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(CreateProductDto createProductDto);

    ProductDto updateProduct(Long productId, UpdateProductDto updateProductDto);

    ProductDto getProductById(Long productId);

    void deleteProduct(Long productId);

    List<ProductDto> getProducts();
}
