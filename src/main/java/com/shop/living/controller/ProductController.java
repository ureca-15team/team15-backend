package com.shop.living.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.living.dto.Product;
import com.shop.living.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ 모든 상품 조회
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // ✅ 특정 상품 상세 조회
    @GetMapping("/{prodcode}")
    public Product getProductById(@PathVariable int prodcode) {
        return productService.getProductById(prodcode);
    }
}
