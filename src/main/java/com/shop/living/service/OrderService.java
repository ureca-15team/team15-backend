package com.shop.living.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.living.dao.OrderDao;
import com.shop.living.dto.Order;
import com.shop.living.dto.OrderItem;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    // ✅ 상세 페이지에서 바로 구매
    public void createOrder(Order order) {
        orderDao.createOrder(order); // orderId 자동 생성됨
        System.out.println("생성된 orderId: " + order.getOrderId());

        // 주문한 상품 정보가 있다면 추가
        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            for (OrderItem item : order.getOrderItems()) {
                orderDao.addOrderItem(order.getOrderId(), item.getProdcode(), item.getQuantity());
            }
        }
    }

    // ✅ 장바구니에서 선택한 상품 구매
    public void createOrderFromCart(String email, List<Integer> prodcodeList) {
        Order order = new Order();
        order.setEmail(email);
        orderDao.createOrder(order);
        int orderId = order.getOrderId();
        System.out.println("장바구니 주문 생성된 orderId: " + orderId);

        for (int prodcode : prodcodeList) {
            orderDao.addOrderItem(orderId, prodcode, 1);
        }

        orderDao.removeMultipleItems(email, prodcodeList);
    }

    // ✅ 구매 목록 조회
    public List<Order> getOrderHistory(String email) {
        List<Order> orders = orderDao.getOrderHistory(email);
        System.out.println("조회된 주문 목록: " + orders);
        return orders;
    }

    // ✅ 구매 취소
    public void cancelOrder(int orderId) {
        orderDao.cancelOrder(orderId);
    }
}
