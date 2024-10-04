package ru.javacode.mvcobjecmapper.product.controller;

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
import ru.javacode.mvcobjecmapper.product.service.ProductService;

@RestController
@RequestMapping(path = "/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(produces = "application/json")
    ResponseEntity<String> createProduct(@RequestBody String createProductJson) {
        return new ResponseEntity<>(productService.createProduct(createProductJson), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{productId}", produces = "application/json")
    ResponseEntity<String> updateProduct(@PathVariable @Min(1) Long productId,
                                         @RequestBody String updateProductJson) {
        return ResponseEntity.ok(productService.updateProduct(productId, updateProductJson));
    }

    @GetMapping(path = "/{productId}", produces = "application/json")
    ResponseEntity<String> getProductById(@PathVariable @Min(1) Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @DeleteMapping(path = "/{productId}")
    ResponseEntity<Void> deleteProduct(@PathVariable @Min(1) Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json")
    ResponseEntity<String> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }
}
