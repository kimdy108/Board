package com.project.board.main.api.controller.member;

import com.project.board.main.api.dto.auth.RefreshAuthToken;
import com.project.board.main.api.dto.user.*;
import com.project.board.main.api.service.member.BoardMemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class BoardMemberController {

    private final BoardMemberService boardMemberService;

    @GetMapping("/info")
    public BoardMemberInfo memberInfo(HttpServletRequest req) {
        return boardMemberService.getMemberInfo(req.getHeader("ugd"));
    }

    @GetMapping("/info/list")
    public List<BoardMemberInfoAll> memberInfoList() {
        return boardMemberService.getMemberInfoAll();
    }

    @PutMapping("/update")
    public String memberUpdate(@RequestBody BoardMemberUpdate boardMemberUpdate, HttpServletRequest req) {
        boardMemberService.updateMemberInfo(boardMemberUpdate, req.getHeader("ugd"));
        return "success";
    }

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

    @PutMapping("/update/password")
    public String updateMemberPassword(@RequestBody BoardMemberUpdatePassword boardMemberUpdatePassword, HttpServletRequest req) {
        boardMemberService.updateMemberPassword(boardMemberUpdatePassword, req.getHeader("ugd"));
        return "success";
    }

    @GetMapping("/check/reset/password")
    public BoardMemberCheckChangePassword checkPossibleToChangePassword(
            @RequestParam String id,
            @RequestParam String userPhone
    ) {
        return boardMemberService.getBoardMemberForResetPassword(id, userPhone);
    }

    @PutMapping("/reset/password")
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

    @PutMapping("/promote/manager")
    public String BoardMemberPromoteManager(@RequestBody BoardMemberChangeRoleManager boardMemberChangeRoleManager, HttpServletRequest req) {
        boardMemberService.BoardMemberPromoteManager(boardMemberChangeRoleManager, req.getHeader("ugd"));
        return "success";
    }

    @PutMapping("/relegate/manager")
    public String BoardMemberRelegateManager(@RequestBody BoardMemberChangeRoleManager boardMemberChangeRoleManager, HttpServletRequest req) {
        boardMemberService.BoardMemberRelegateManager(boardMemberChangeRoleManager, req.getHeader("ugd"));
        return "success";
    }

    @GetMapping("/info/manage")
    public BoardMemberInfo memberManageInfo(@RequestParam String userGuid) {
        return boardMemberService.getMemberManageInfo(userGuid);
    }

    @PostMapping("/join/manage")
    public String joinManage(@RequestBody BoardMemberJoin boardMemberJoin) {
        return boardMemberService.joinMember(boardMemberJoin);
    }

    @PutMapping("/update/manage")
    public String memberManageUpdate(@RequestBody BoardMemberManageUpdate boardMemberManageUpdate) {
        boardMemberService.updateMemberManageInfo(boardMemberManageUpdate);
        return "success";
    }

    @PutMapping("/reset/password/manage")
    public String memberManageResetPassword(@RequestBody BoardMemberManageResetPassword boardMemberManageResetPassword) {
        boardMemberService.resetMemberManagePassword(boardMemberManageResetPassword.getUserGuid());
        return "success";
    }

    @DeleteMapping("/manage/delete/{userGuid}")
    public String deleteMember(@PathVariable String userGuid) {
        boardMemberService.deleteMember(userGuid);
        return "success";
    }
}
