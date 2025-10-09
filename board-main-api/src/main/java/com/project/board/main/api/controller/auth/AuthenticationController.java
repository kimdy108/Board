package com.project.board.main.api.controller.auth;

import com.project.board.main.api.dto.auth.UserAuth;
import com.project.board.main.api.dto.common.ResponseMsg;
import com.project.board.main.api.dto.auth.UserLogin;
import com.project.board.main.api.dto.auth.UserRefresh;
import com.project.board.main.api.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "인증 관리 컨트롤러", description = "인증 API Controller 입니다.")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "user login", description = "사용자 로그인")
    @PostMapping("/login")
    public ResponseEntity<ResponseMsg> login(@RequestBody UserLogin userLogin, HttpServletResponse response) {
        UserAuth userAuth = authenticationService.login(userLogin);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("accesstoken", userAuth.getAccessToken());
        response.setHeader("refreshtoken", userAuth.getRefreshToken());

        return ResponseMsg.successResponse(userAuth.getLoginResult());
    }

    @Operation(summary = "user refresh", description = "사용자 재로그인")
    @PostMapping("/refresh")
    public ResponseEntity<ResponseMsg> refresh(@RequestBody UserRefresh userRefresh, HttpServletResponse response) {
        response.setHeader("accesstoken", authenticationService.refreshToken(userRefresh));

        return ResponseMsg.successResponse("success");
    }
}
