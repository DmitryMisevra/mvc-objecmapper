package ru.javacode.mvcobjecmapper.product.service;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javacode.mvcobjecmapper.exception.ResourceNotFoundException;
import ru.javacode.mvcobjecmapper.product.dto.CreateProductDto;
import ru.javacode.mvcobjecmapper.product.dto.ProductDto;
import ru.javacode.mvcobjecmapper.product.dto.UpdateProductDto;
import ru.javacode.mvcobjecmapper.product.mapper.ProductMapper;
import ru.javacode.mvcobjecmapper.product.model.Product;
import ru.javacode.mvcobjecmapper.product.repository.ProductRepository;
import ru.javacode.mvcobjecmapper.util.JsonUtils;
import ru.javacode.mvcobjecmapper.util.ValidationUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final Validator validator;

    @Override
    public String createProduct(String createProductJson) {
        CreateProductDto createProductDto = JsonUtils.fromJson(createProductJson, CreateProductDto.class);
        ValidationUtils.validate(validator, createProductDto);
        Product product = productMapper.createProductDtoToProduct(createProductDto);
        Product savedProduct = productRepository.save(product);
        ProductDto productDto = productMapper.productToProductDto(savedProduct);
        return JsonUtils.toJson(productDto);
    }

    @Override
    public String updateProduct(Long productId, String updateProductJson) {
        UpdateProductDto updateProductDto = JsonUtils.fromJson(updateProductJson, UpdateProductDto.class);
        ValidationUtils.validate(validator, updateProductDto);
        Product productToUpdate = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(
                "Товар с id " + productId + " не найден"));
        if (updateProductDto.getName() != null) {
            productToUpdate.setName(updateProductDto.getName());
        }
        if (updateProductDto.getDescription() != null) {
            productToUpdate.setName(updateProductDto.getDescription());
        }
        if (updateProductDto.getDescription() != null) {
            productToUpdate.setName(updateProductDto.getDescription());
        }
        if (updateProductDto.getPrice() != null) {
            productToUpdate.setPrice(updateProductDto.getPrice());
        }
        if (updateProductDto.getQuantityInStock() != null) {
            productToUpdate.setQuantityInStock(updateProductDto.getQuantityInStock());
        }
        Product savedProduct = productRepository.save(productToUpdate);
        ProductDto productDto = productMapper.productToProductDto(savedProduct);
        return JsonUtils.toJson(productDto);
    }

    @Transactional(readOnly = true)
    @Override
    public String getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(
                "Товар с id " + productId + " не найден"));
        ProductDto productDto = productMapper.productToProductDto(product);
        return JsonUtils.toJson(productDto);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Transactional(readOnly = true)
    @Override
    public String getProducts() {
        List<ProductDto> products = productRepository.findAll().stream()
                .map(productMapper::productToProductDto)
                .toList();
        return JsonUtils.toJson(products);
    }
}
