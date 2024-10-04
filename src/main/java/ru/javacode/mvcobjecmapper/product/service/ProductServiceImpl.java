package ru.javacode.mvcobjecmapper.product.service;

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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto createProduct(CreateProductDto createProductDto) {
        Product product = productMapper.createProductDtoToProduct(createProductDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long productId, UpdateProductDto updateProductDto) {
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
        return productMapper.productToProductDto(savedProduct);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(
                "Товар с id " + productId + " не найден"));
        return productMapper.productToProductDto(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::productToProductDto)
                .toList();
    }
}
