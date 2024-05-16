package com.study.board.service;

import com.study.board.entity.Member;
import com.study.board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member memberLogin(String id, String password) {
        return memberRepository.findByIdAndPassword(id, password);
    }

    public String memberJoin(String id, String nickname, String password, String username, String phone_num) {
        if (!checkId(id)) {
            return "이미 존재하는 아이디입니다.";
        } else if (!checkNickname(nickname)) {
            return "이미 존재하는 닉네임입니다.";
        } else {
            Member member = new Member();
            member.setId(id);
            member.setNickname(nickname);
            member.setPassword(password);
            member.setUsername(username);
            member.setPhone_num(phone_num);
            memberRepository.save(member);
            return "success";
        }
    }

    public boolean checkId(String id) {
        Member memberIdCheck = memberRepository.findById(id);
        boolean checkid = false;

        if (memberIdCheck == null) {
            checkid = true;
        }

        return checkid;
    }

    public boolean checkNickname(String nickname) {
        Member memberNicknameCheck = memberRepository.findByNickname(nickname);
        boolean checknickname = false;

        if (memberNicknameCheck == null) {
            checknickname = true;
        }

        return checknickname;
    }
}
