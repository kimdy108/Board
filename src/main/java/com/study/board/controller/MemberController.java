package com.study.board.controller;

import com.study.board.entity.Member;
import com.study.board.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public String firstPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "loginpage";
    }

    @GetMapping("/signup")
    public String singup() {
        return "signup";
    }

    @PostMapping("/member/login")
    public String login(String id,String password, Model model) {

        Member result = memberService.memberLogin(id, password);

        if (result != null) {
            log.info("username : " + result.getNickname());
            return "redirect:/board/list";
        } else {
            log.error("noMember");
            model.addAttribute("message", "아이디 또는 비밀번호를 확인해주세요.");
            model.addAttribute("searchUrl", "/login");
            return "message";
        }
    }

    @PostMapping("/signup/member")
    public String signupmember(
            String id ,
            String nickname,
            String password,
            String username,
            String phone_num,
            Model model
    ) {

        String result = memberService.memberJoin(id, nickname, password, username, phone_num);

        if (!result.equals("success")) {
            model.addAttribute("message", result);
            model.addAttribute("id", id);
            model.addAttribute("nickname", nickname);
            model.addAttribute("password", password);
            model.addAttribute("username", username);
            model.addAttribute("phone_num", phone_num);
            return "signup";
        } else {
            model.addAttribute("message", "회원가입이 완료되었습니다.");
            model.addAttribute("searchUrl", "/login");
            return "message";
        }
    }
}
