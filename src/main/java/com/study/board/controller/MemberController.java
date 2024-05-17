package com.study.board.controller;

import com.study.board.entity.Member;
import com.study.board.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/find/id")
    public String findId() {
        return "findid";
    }

    @GetMapping("/find/password")
    public String findPassword() {
        return "findpassword";
    }

    @PostMapping("/member/login")
    public String login(
            @RequestParam("id") String id,
            @RequestParam("password") String password,
            Model model) {

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
            @RequestParam("id") String id ,
            @RequestParam("nickname") String nickname,
            @RequestParam("password") String password,
            @RequestParam("username") String username,
            @RequestParam("phone_num") String phoneNum,
            Model model
    ) {

        String result = memberService.memberJoin(id, nickname, password, username, phoneNum);

        if (!result.equals("success")) {
            model.addAttribute("message", result);
            model.addAttribute("id", id);
            model.addAttribute("nickname", nickname);
            model.addAttribute("password", password);
            model.addAttribute("username", username);
            model.addAttribute("phone_num", phoneNum);
            return "signup";
        } else {
            model.addAttribute("message", "회원가입이 완료되었습니다.");
            model.addAttribute("searchUrl", "/login");
            return "message";
        }
    }

    @GetMapping("/find/member/id")
    public String findMemberId(
            @RequestParam("username") String username,
            @RequestParam("phone_num") String phoneNum,
            Model model) {

        Member result = memberService.findMemberId(username, phoneNum);

        if (result != null) {
            model.addAttribute("message", "아이디는 " + result.getId() + " 입니다.");
            model.addAttribute("searchUrl", "/login");
        } else {
            model.addAttribute("message", "입력하신 정보와 일치하는 회원이 없습니다.");
            model.addAttribute("searchUrl", "/find/id");
        }
        return "message";
    }

    @GetMapping("/find/member/password")
    public String findMemberPassword(
            @RequestParam("id") String id,
            @RequestParam("username") String username,
            @RequestParam("phone_num") String phoneNum,
            Model model) {

        Member result = memberService.findMemberPassword(id, username, phoneNum);

        if (result != null) {
            model.addAttribute("message", "비밀번호는 " + result.getPassword() + " 입니다.");
            model.addAttribute("searchUrl", "/login");
        } else {
            model.addAttribute("message", "입력하신 정보와 일치하는 회원이 없습니다.");
            model.addAttribute("searchUrl", "/find/password");
        }
        return "message";
    }
}
