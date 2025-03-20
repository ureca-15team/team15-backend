package com.shop.living.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.living.dao.CartDao;
import com.shop.living.dto.Cart;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    public void addToCart(Cart cart) {
        cartDao.addToCart(cart);
    }

    public List<Cart> getCartItems(String email) {
        return cartDao.getCartItems(email);
    }

    public int removeFromCart(int cartId) {
        return cartDao.removeFromCart(cartId);
    }

}
