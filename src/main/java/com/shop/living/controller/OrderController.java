package com.shop.living.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.living.dto.Member;
import com.shop.living.dto.Order;
import com.shop.living.dto.OrderItem;
import com.shop.living.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ✅ 상세 페이지에서 개별 상품 구매 (상품을 바로 구매하는 경우)
    @PostMapping
    public String purchaseProduct(@RequestBody Order order, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("member") == null) {
            return "로그인이 필요합니다.";
        }

        Member member = (Member) session.getAttribute("member");
        order.setEmail(member.getEmail());

        orderService.createOrder(order);
        return "구매가 완료되었습니다.";
    }


    // ✅ 구매 목록 조회 (내가 구매한 상품들 조회)
    @GetMapping
    public List<Order> getOrderHistory(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("member") == null) {
            return null;
        }

        Member member = (Member) session.getAttribute("member");
        String email = member.getEmail();

        List<Order> orders = orderService.getOrderHistory(email);
        
        // ✅ 주문 목록 디버깅 로그 추가
        for (Order order : orders) {
            System.out.println("✅ 주문 ID: " + order.getOrderId() + 
                               ", 주문한 상품 개수: " + (order.getOrderItems() != null ? order.getOrderItems().size() : "0"));
            for (OrderItem item : order.getOrderItems()) {
                System.out.println("   🔹 주문 상세: " + item.toString());
            }
        }

        return orders;
    }



    // ✅ 구매 취소 (구매한 상품을 취소하는 기능)
    @DeleteMapping("/{orderId}")
    public String cancelOrder(@PathVariable int orderId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("member_email") == null) {
            return "로그인이 필요합니다.";
        }
        orderService.cancelOrder(orderId);
        return "구매가 취소되었습니다.";
    }
}
