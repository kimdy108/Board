package com.project.board.main.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRefreshAuth {
    private String accessToken;
    private String refreshToken;

    public static UserRefreshAuth create(String accessToken, String refreshToken) {
        UserRefreshAuth userRefreshAuth = new UserRefreshAuth();
        userRefreshAuth.setAccessToken(accessToken);
        userRefreshAuth.setRefreshToken(refreshToken);
        return userRefreshAuth;
    }
}
