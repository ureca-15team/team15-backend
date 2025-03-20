package com.shop.living.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.living.dao.MemberDao;
import com.shop.living.dto.Member;
import com.shop.living.util.PasswordUtil;

@Service
public class MemberService {

    @Autowired
    private MemberDao memberDao;

    // 회원가입
    public void insertMember(Member member) throws Exception {
        String salt = PasswordUtil.generateSalt(); // 랜덤 Salt 생성
        String hashedPwd = PasswordUtil.hashPassword(member.getPwd(), salt); // Salt 적용하여 해싱

        member.setPwd(hashedPwd);
        member.setSalt(salt); // Salt 저장

        // DB에 salt 값도 함께 저장
        memberDao.insertMember(member);
    }
    
    // 이메일 중복 확인
    public boolean isEmailAvailable(String email) {
        Member existingMember = memberDao.getMemberByEmail(email);
        return existingMember == null; // 존재하지 않으면 사용 가능
    }

    // 로그인 
    public Member login(Member member) throws Exception {
        Member storedMember = memberDao.getMemberByEmail(member.getEmail());
        if (storedMember != null) {
            String inputPwd = member.getPwd();
            String storedHash = storedMember.getPwd();
            String storedSalt = storedMember.getSalt(); // ✅ Salt 가져오기

            // 입력된 비밀번호를 같은 salt로 해싱한 후 비교
            if (PasswordUtil.checkPassword(inputPwd, storedHash, storedSalt)) {
                return storedMember; // 비밀번호 일치하면 로그인 성공
            }
        }
        return null;
    }
}
