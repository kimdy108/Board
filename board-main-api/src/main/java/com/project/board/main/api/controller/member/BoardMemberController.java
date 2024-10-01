package com.project.board.main.api.controller.member;

import com.project.board.main.api.domain.member.BoardMember;
import com.project.board.main.api.dto.auth.RefreshAuthToken;
import com.project.board.main.api.dto.member.*;
import com.project.board.main.api.service.member.BoardMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class BoardMemberController {

    private final BoardMemberService boardMemberService;

    @PostMapping("/login")
    public BoardMemberSuccessLogin login(@RequestBody BoardMemberLogin boardMemberLogin) {
        return boardMemberService.login(boardMemberLogin);
    }

    @PostMapping("/join")
    public String join(@RequestBody BoardMemberJoin boardMemberJoin) {
        return boardMemberService.join(boardMemberJoin);
    }

    @DeleteMapping("/delete/{guid}")
    public String delete(@PathVariable String guid, @RequestBody BoardMemberSignOut boardMemberSignOut) {
        boardMemberService.delete(guid, boardMemberSignOut.getPassword());
        return "success";
    }

    @GetMapping("/check/change/password")
    public BoardMemberCheckChangePassword checkPossibleToChangePassword(
            @RequestParam String id,
            @RequestParam String userPhone
    ) {
        return boardMemberService.getBoardMemberForResetPassword(id, userPhone);
    }

    @PutMapping("/change/password")
    public String changePassword(@RequestBody BoardMemberChangePassword boardMemberChangePassword) {
        boardMemberService.updatePassword(boardMemberChangePassword);
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

    @PostMapping("/access/token/refresh")
    public BoardMemberSuccessRefreshToken refreshAuth(@RequestBody RefreshAuthToken refreshAuthToken) {
        return boardMemberService.refreshAuth(refreshAuthToken);
    }
}
