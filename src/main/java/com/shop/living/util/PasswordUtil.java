package com.shop.living.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    private static final int SALT_LENGTH = 16; // Salt 길이 지정

    // 랜덤한 Salt 생성
    public static String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        sr.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        return encodedSalt;
    }

    // Salt와 함께 비밀번호 해싱
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String saltedPassword = salt + password;  // Salt + 비밀번호 결합

        md.update(saltedPassword.getBytes()); // 해싱할 데이터 적용
        byte[] byteData = md.digest();

        // 해싱된 데이터를 16진수 문자열로 변환
        StringBuilder sb = new StringBuilder();
        for (byte b : byteData) {
            sb.append(String.format("%02x", b));
        }

        String hashedPassword = sb.toString();

        return hashedPassword;
    }

    // 비밀번호 검증 
    public static boolean checkPassword(String inputPwd, String storedHash, String storedSalt) throws NoSuchAlgorithmException {

        String hashedInputPwd = hashPassword(inputPwd, storedSalt);
        boolean isMatch = hashedInputPwd.equals(storedHash);

        return isMatch;
    }
}
