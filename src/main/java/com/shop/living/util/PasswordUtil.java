package com.shop.living.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    private static final int SALT_LENGTH = 16; // Salt 길이 지정

    // ✅ 랜덤한 Salt 생성
    public static String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        sr.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        System.out.println("🔹 [디버깅] 생성된 Salt: " + encodedSalt);
        return encodedSalt;
    }

    // ✅ Salt와 함께 비밀번호 해싱 (디버깅용 로그 추가)
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        System.out.println("🔹 [디버깅] 원래 비밀번호: " + password);
        System.out.println("🔹 [디버깅] 사용된 Salt: " + salt);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String saltedPassword = salt + password;  // Salt + 비밀번호 결합
        System.out.println("🔹 [디버깅] Salt + 비밀번호: " + saltedPassword);

        md.update(saltedPassword.getBytes()); // 해싱할 데이터 적용
        byte[] byteData = md.digest();

        // 해싱된 데이터를 16진수 문자열로 변환
        StringBuilder sb = new StringBuilder();
        for (byte b : byteData) {
            sb.append(String.format("%02x", b));
        }

        String hashedPassword = sb.toString();
        System.out.println("🔹 [디버깅] 해싱된 비밀번호(SHA-256 적용): " + hashedPassword);

        return hashedPassword;
    }

    // ✅ 비밀번호 검증 (입력된 비밀번호 + 저장된 salt로 해싱하여 비교)
    public static boolean checkPassword(String inputPwd, String storedHash, String storedSalt) throws NoSuchAlgorithmException {
        System.out.println("✅ [디버깅] 비밀번호 검증 시작...");
        System.out.println("✅ [디버깅] 입력된 비밀번호: " + inputPwd);
        System.out.println("✅ [디버깅] 저장된 Salt: " + storedSalt);
        System.out.println("✅ [디버깅] 저장된 해싱된 비밀번호: " + storedHash);

        String hashedInputPwd = hashPassword(inputPwd, storedSalt);
        boolean isMatch = hashedInputPwd.equals(storedHash);

        System.out.println("✅ [디버깅] 검증 결과: " + (isMatch ? "✅ 일치함" : "❌ 불일치함"));

        return isMatch;
    }
}
