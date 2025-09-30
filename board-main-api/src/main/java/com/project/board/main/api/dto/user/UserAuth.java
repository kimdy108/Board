package com.project.board.main.api.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserAuth {
    private String accessToken;
    private String refreshToken;
    private String loginResult;

    @Builder
    public UserAuth(String accessToken, String refreshToken, String loginResult) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.loginResult = loginResult;
    }
}
