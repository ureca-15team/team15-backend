package com.shop.living.service;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5; // 최대 허용 로그인 실패 횟수
    private static final long LOCK_TIME_MINUTES = 1; // 로그인 차단 시간 (5분)

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 로그인 실패 횟수 증가
    public void increaseFailedAttempts(String email) {
        String key = "login_attempts:" + email;
        Long attempts = redisTemplate.opsForValue().increment(key); // 실패 횟수 증가
        if (attempts == 1) {
            redisTemplate.expire(key, Duration.ofMinutes(LOCK_TIME_MINUTES)); // 만료 시간 설정
        }
    }

    // 현재 로그인 실패 횟수 가져오기
    public int getFailedAttempts(String email) {
        String key = "login_attempts:" + email;
        String attempts = redisTemplate.opsForValue().get(key);
        return attempts == null ? 0 : Integer.parseInt(attempts);
    }

    // 로그인 차단 여부 확인
    public boolean isBlocked(String email) {
        return getFailedAttempts(email) >= MAX_ATTEMPTS;
    }

    // 로그인 성공 시 실패 횟수 초기화
    public void resetAttempts(String email) {
        String key = "login_attempts:" + email;
        redisTemplate.delete(key);
    }
}