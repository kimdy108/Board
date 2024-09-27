package com.project.board.main.api.controller.member;

import com.project.board.main.api.domain.member.BoardMember;
import com.project.board.main.api.dto.member.BoardMemberChangePassword;
import com.project.board.main.api.dto.member.BoardMemberJoin;
import com.project.board.main.api.dto.member.BoardMemberLogin;
import com.project.board.main.api.service.member.BoardMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class BoardMemberController {

    private final BoardMemberService loginService;
    private final BoardMemberService boardMemberService;

    @PostMapping("/login")
    public String login(@RequestBody BoardMemberLogin boardMemberLogin) {
        loginService.login(boardMemberLogin);
        return "success";
    }

    @PostMapping("/join")
    public String join(@RequestBody BoardMemberJoin boardMemberJoin) {
        return boardMemberService.join(boardMemberJoin);
    }

    @GetMapping("/check/change/password")
    public BoardMember checkPossibleToChangePassword(
            @RequestParam String id,
            @RequestParam String userPhone
    ) {
        return loginService.getBoardMemberForResetPassword(id, userPhone);
    }

    @PutMapping("/change/password")
    public String changePassword(@RequestBody BoardMemberChangePassword boardMemberChangePassword) {
        loginService.updatePassword(boardMemberChangePassword);
        return "success";
    }

    @GetMapping("/check/join/id")
    public boolean checkPossibleToJoinId(@RequestParam String id) {
        return boardMemberService.checkJoinToId(id);
    }

    @GetMapping("/check/join/nickname")
    public boolean checkPossibleToJoinNickName(@RequestParam String nickName) {
        return boardMemberService.checkJoinToNickName(nickName);
    }

    @GetMapping("/check/join/phone")
    public boolean checkPossibleToJoinPhone(@RequestParam String phone) {
        return boardMemberService.checkJoinToPhone(phone);
    }
}
