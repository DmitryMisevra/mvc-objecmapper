package ru.javacode.mvcobjecmapper.product.mapper;

import org.springframework.stereotype.Component;
import ru.javacode.mvcobjecmapper.product.dto.CreateProductDto;
import ru.javacode.mvcobjecmapper.product.dto.ProductDto;
import ru.javacode.mvcobjecmapper.product.model.Product;

@Component
public class ProductMapper {

    public Product createProductDtoToProduct(CreateProductDto createProductDto) {
        return Product.builder()
                .name(createProductDto.getName())
                .description(createProductDto.getDescription())
                .price(createProductDto.getPrice())
                .quantityInStock(createProductDto.getQuantityInStock())
                .build();
    }

    public ProductDto productToProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantityInStock(product.getQuantityInStock())
                .build();
    }
}
