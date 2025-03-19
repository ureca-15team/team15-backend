package com.shop.living.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.shop.living.dto.Cart;

@Mapper
public interface CartDao {

    @Insert("INSERT INTO cart (email, prodcode, quantity) VALUES (#{email}, #{prodcode}, #{quantity}) ON DUPLICATE KEY UPDATE quantity = quantity + #{quantity}")
    @Options(useGeneratedKeys = true, keyProperty = "cartId")
    void addToCart(Cart cart);

    @Results(id = "cartResultMap", value = {
        @Result(property = "cartId", column = "cart_id"),
        @Result(property = "email", column = "email"),
        @Result(property = "prodcode", column = "prodcode"),
        @Result(property = "quantity", column = "quantity")
    })
    @Select("SELECT cart_id, email, prodcode, quantity FROM cart WHERE email = #{email}")
    List<Cart> getCartItems(String email);

    @Delete("DELETE FROM cart WHERE cart_id = #{cartId}")
    int removeFromCart(int cartId);
}
