package com.shop.living.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5; // 최대 허용 로그인 실패 횟수
    private static final long LOCK_TIME_MINUTES = 1; // 로그인 차단 시간 (분)

    private final Map<String, Integer> attemptsCache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // 로그인 실패 횟수 증가
    public void increaseFailedAttempts(String email) {
        attemptsCache.put(email, getFailedAttempts(email) + 1);

        if (getFailedAttempts(email) == 1) {
            scheduleUnlock(email);
        }
    }

    // 현재 로그인 실패 횟수 가져오기
    public int getFailedAttempts(String email) {
        return attemptsCache.getOrDefault(email, 0);
    }

    // 로그인 차단 여부 확인
    public boolean isBlocked(String email) {
        return getFailedAttempts(email) >= MAX_ATTEMPTS;
    }

    // 로그인 성공 시 실패 횟수 초기화
    public void resetAttempts(String email) {
        attemptsCache.remove(email);
    }

    // 일정 시간이 지나면 자동으로 차단 해제
    private void scheduleUnlock(String email) {
        scheduler.schedule(() -> attemptsCache.remove(email), LOCK_TIME_MINUTES, TimeUnit.MINUTES);
    }
}