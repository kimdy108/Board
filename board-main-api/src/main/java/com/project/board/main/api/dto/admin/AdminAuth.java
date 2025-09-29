package com.project.board.main.api.dto.admin;

import com.project.board.main.api.dto.constant.admin.AdminRole;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AdminAuth {
    private String accessToken;
    private String refreshToken;
    private String loginResult;

    @Builder
    public AdminAuth(String accessToken, String refreshToken, String loginResult) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.loginResult = loginResult;
    }
}
