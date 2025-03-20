package com.shop.living.dto;

public class Cart {
    private int cartId;
    private String email;
    private int prodcode;
    private int quantity;

    public Cart() {}

    // cartId까지 포함하는 생성자 추가
    public Cart(int cartId, String email, int prodcode, int quantity) {
        this.cartId = cartId;
        this.email = email;
        this.prodcode = prodcode;
        this.quantity = quantity;
    }

    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getProdcode() { return prodcode; }
    public void setProdcode(int prodcode) { this.prodcode = prodcode; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "Cart [cartId=" + cartId + ", email=" + email + ", prodcode=" + prodcode + ", quantity=" + quantity + "]";
    }
}
