package com.shop.living.dto;

public class OrderItem {
    private int itemId;
    private int orderId;
    private int prodcode;
    private int quantity;

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getProdcode() { return prodcode; }
    public void setProdcode(int prodcode) { this.prodcode = prodcode; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId=" + (itemId > 0 ? itemId : "NULL") +
                ", orderId=" + (orderId > 0 ? orderId : "NULL") +
                ", prodcode=" + prodcode +
                ", quantity=" + quantity +
                '}';
    }
}
