package com.shop.living.dto;

import java.io.Serializable;

public class Member implements Serializable {
    private static final long serialVersionUID = 1L; // 직렬화 버전 UID 추가

    private String email;
    private String pwd;
    private String nickname;
    private String salt;

    // 기본 생성자
    public Member() {}

    // 매개변수 생성자
    public Member(String email, String pwd, String nickname) {
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
    }

    // Getter & Setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getSalt() { return salt; } 
    public void setSalt(String salt) { this.salt = salt; }  

    @Override
    public String toString() {
        return "Member [email=" + email + ", nickname=" + nickname + "]";
    }
}