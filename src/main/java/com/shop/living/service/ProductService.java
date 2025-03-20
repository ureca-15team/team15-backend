package com.shop.living.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.living.dao.ProductDao;
import com.shop.living.dto.Product;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    // 모든 상품 조회
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    // 특정 상품 상세 조회
    public Product getProductById(int prodcode) {
        return productDao.getProductById(prodcode);
    }
}
