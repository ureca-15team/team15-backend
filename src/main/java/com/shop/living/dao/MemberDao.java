package com.shop.living.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.shop.living.dto.Member;

@Mapper
public interface MemberDao {

    // ✅ 회원가입 (salt도 함께 저장)
    @Insert("INSERT INTO member (email, pwd, nickname, salt) VALUES (#{email}, #{pwd}, #{nickname}, #{salt})")
    void insertMember(Member member) throws Exception;

    // ✅ 이메일로 회원 정보 조회 (salt 값도 포함)
    @Select("SELECT email, pwd, nickname, salt FROM member WHERE email = #{email}")
    Member getMemberByEmail(String email);
}
