package com.project.board.main.api.controller.auth;

import com.project.board.main.api.dto.admin.AdminAuth;
import com.project.board.main.api.dto.admin.AdminLogin;
import com.project.board.main.api.dto.admin.AdminRefresh;
import com.project.board.main.api.dto.common.ResponseMsg;
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
@Tag(name = "인증 및 사용자 관리 컨트롤러", description = "인증 및 사용자 API Controller 입니다.")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "admin login", description = "사용자 로그인")
    @PostMapping("/login")
    public ResponseEntity<ResponseMsg> login(@RequestBody AdminLogin adminLogin, HttpServletResponse res) {
        AdminAuth adminAuth = authenticationService.login(adminLogin);

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.setHeader("accesstoken", adminAuth.getAccessToken());
        res.setHeader("refreshtoken", adminAuth.getRefreshToken());

        return ResponseMsg.successResponse(adminAuth.getLoginResult());
    }

    @Operation(summary = "admin refresh", description = "사용자 재로그인")
    @PostMapping("/refresh")
    public ResponseEntity<ResponseMsg> refresh(@RequestBody AdminRefresh adminRefresh, HttpServletResponse res) {
        res.setHeader("accesstoken", authenticationService.refreshToken(adminRefresh));

        return ResponseMsg.successResponse("success");
    }
}
