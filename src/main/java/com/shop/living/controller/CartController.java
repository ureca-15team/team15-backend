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

import com.shop.living.dto.Cart;
import com.shop.living.dto.Member;
import com.shop.living.service.CartService;
import com.shop.living.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    // ✅ 장바구니에 상품 추가
    @PostMapping("/add")
    public String addToCart(@RequestBody Cart cart, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("member") == null) {
            System.out.println("❌ 세션이 존재하지 않거나, 로그인 정보가 없음");
            return "로그인이 필요합니다.";
        }

        Member member = (Member) session.getAttribute("member"); // ✅ 세션에서 회원 객체 가져오기
        System.out.println("✅ 세션 ID: " + session.getId() + ", 이메일: " + member.getEmail());

        cart.setEmail(member.getEmail());
        cartService.addToCart(cart);
        return "장바구니에 추가되었습니다.";
    }


    // ✅ 장바구니 목록 조회
    @GetMapping
    public List<Cart> getCartItems(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("member") == null) {
            System.out.println("❌ 세션이 존재하지 않음 또는 로그인되지 않음");
            return null;
        }

        Member member = (Member) session.getAttribute("member");
        System.out.println("✅ 세션 ID: " + session.getId() + ", 이메일: " + member.getEmail());

        return cartService.getCartItems(member.getEmail());
    }

    // ✅ 장바구니에서 특정 상품 삭제
    @DeleteMapping("/{cartId}")
    public String removeFromCart(@PathVariable int cartId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("member") == null) {
            System.out.println("❌ 세션이 존재하지 않음 또는 로그인되지 않음");
            return "로그인이 필요합니다.";
        }

        System.out.println("✅ 삭제 요청: cartId = " + cartId);

        int result = cartService.removeFromCart(cartId);
        if (result > 0) {
            return "장바구니에서 삭제되었습니다.";
        } else {
            return "삭제할 상품이 존재하지 않습니다.";
        }
    }


 // ✅ 장바구니에서 선택한 상품 구매
    @PostMapping("/checkout")
    public String checkoutCart(@RequestBody List<Integer> prodcodeList, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("member") == null) {
            return "로그인이 필요합니다.";
        }

        // ✅ `Member` 객체를 가져온 후, `email`을 추출해야 함
        Member member = (Member) session.getAttribute("member");
        String email = member.getEmail(); // ✅ 올바른 방식

        orderService.createOrderFromCart(email, prodcodeList);
        return "선택한 상품이 구매 목록에 추가되었습니다.";
    }


}
