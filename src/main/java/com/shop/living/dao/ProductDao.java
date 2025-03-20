package com.shop.living.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.shop.living.dto.Product;

@Mapper
public interface ProductDao {

    // 모든 상품 조회
    @Select("SELECT * FROM product")
    List<Product> getAllProducts();

    // 특정 상품 상세 조회
    @Select("SELECT * FROM product WHERE prodcode = #{prodcode}")
    Product getProductById(int prodcode);
}
