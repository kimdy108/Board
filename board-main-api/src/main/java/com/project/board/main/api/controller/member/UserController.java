package com.project.board.main.api.controller.member;

import com.project.board.main.api.dto.common.ResponseMsg;
import com.project.board.main.api.dto.member.user.UserApproval;
import com.project.board.main.api.dto.member.user.UserSignUp;
import com.project.board.main.api.service.member.UserService;
import com.project.board.main.api.utils.jwt.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/user")
@Tag(name = "사용자 관리 컨트롤러", description = "사용자 API Controller 입니다.")
public class UserController {
    private final UserService userService;

    @Operation(summary = "user signup", description = "사용자 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ResponseMsg> userSignUp(@RequestBody UserSignUp userSignUp) {
        userService.userSignUp(userSignUp);
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "user update approval", description = "사용자 승인 변경")
    @PutMapping("/update/approval")
    public ResponseEntity<ResponseMsg> userUpdateApproval(@RequestBody UserApproval userApproval) {
        userService.userApproval(userApproval);
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "user info", description = "사용자 정보")
    @GetMapping("/info/{userUUID}")
    public ResponseEntity<ResponseMsg> userInfo(@PathVariable UUID userUUID) {
        return ResponseMsg.successResponse(userService.userInfo(userUUID));
    }

    @Operation(summary = "user list page", description = "사용자 리스트 페이지")
    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> userListPage(
            @RequestParam String searchType,
            @RequestParam String searchValue
    ) {
        return ResponseMsg.successResponse(userService.userListPage(searchType, searchValue));
    }
}
