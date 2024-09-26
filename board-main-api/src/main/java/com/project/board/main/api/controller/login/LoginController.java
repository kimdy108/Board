package com.project.board.main.api.controller.login;

import com.project.board.main.api.domain.login.BoardMember;
import com.project.board.main.api.dto.login.BoardMemberChangePassword;
import com.project.board.main.api.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("")
    public String login() {
        loginService.login();
        return "success";
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
}
