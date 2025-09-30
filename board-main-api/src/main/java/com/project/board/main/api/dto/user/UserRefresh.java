package com.project.board.main.api.dto.user;

import lombok.Getter;

@Getter
public class UserRefresh {
    private String userAccount;
    private String refreshToken;
}
