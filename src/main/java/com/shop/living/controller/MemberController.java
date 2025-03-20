package com.shop.living.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.living.dto.Member;
import com.shop.living.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // ✅ 회원가입
    @PostMapping("/signup")
    public String signup(@RequestBody Member member) {
        try {
            memberService.insertMember(member);
            return member.getNickname() + "님 가입을 환영합니다!";
        } catch (Exception e) {
            e.printStackTrace();
            return "회원가입 실패";
        }
    }
    
    // ✅ 이메일 중복 확인 API
    @PostMapping("/check-email") // ✅ GET -> POST로 변경
    public Map<String, Object> checkEmail(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String email = request.get("email");

        if (email == null || email.trim().isEmpty()) {
            response.put("available", false);
            response.put("message", "이메일을 입력해주세요.");
            return response;
        }

        boolean isAvailable = memberService.isEmailAvailable(email);
        response.put("available", isAvailable);
        response.put("message", isAvailable ? "사용 가능한 이메일입니다." : "이미 사용 중인 이메일입니다.");
        return response;
    }


    // ✅ 로그인 (세션 방식)
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Member member, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            Member loggedInMember = memberService.login(member);
            if (loggedInMember != null) {
                HttpSession session = request.getSession();
                session.setAttribute("member", loggedInMember);

                response.put("nickname", loggedInMember.getNickname());
                response.put("message", "로그인 성공");
                return ResponseEntity.ok(response); // 200 OK (로그인 성공)
            } else {
                response.put("message", "이메일 또는 비밀번호를 확인해주세요.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401 Unauthorized (로그인 실패)
            }
        } catch (Exception e) {
            response.put("message", "서버 오류 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 Internal Server Error
        }
    }
    

    // ✅ 로그아웃 (세션 삭제)
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return "로그아웃 성공";
        }
        return "로그인 상태가 아닙니다.";
    }
    


    // ✅ 로그인 상태 확인
    @GetMapping("/status")
    public Map<String, String> checkLoginStatus(HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("member") != null) {
            Member member = (Member) session.getAttribute("member");
            response.put("nickname", member.getNickname());
            response.put("message", "로그인 상태 유지 중");
        } else {
            response.put("message", "로그인되지 않은 상태입니다.");
        }
        return response;
    }
    
    // ✅ 🔥 **세션 유지 시간 확인 기능 추가!**
    @GetMapping("/session-time")
    public Map<String, String> getSessionTime(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Map<String, String> response = new HashMap<>();

        if (session != null) {
            int timeout = session.getMaxInactiveInterval(); // 초 단위 유지 시간
            response.put("message", "세션이 유지 중입니다.");
            response.put("sessionTimeout", timeout + "초"); // 초 단위 출력
        } else {
            response.put("message", "세션이 존재하지 않습니다.");
        }

        return response;
    }
}
