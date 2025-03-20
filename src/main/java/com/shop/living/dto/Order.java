package com.shop.living.dto;

import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private String email;
    private Date orderDate;
    private List<OrderItem> orderItems; // 주문한 상품 리스트 추가

    public Order() {}

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", email='" + email + '\'' +
                ", orderDate=" + orderDate +
                ", orderItems=" + (orderItems != null ? orderItems.toString() : "[]") +
                '}';
    }
}
