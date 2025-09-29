package com.project.board.main.api.dto.constant.admin;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Arrays;

@Getter
@Accessors(fluent = true)
public enum AdminRole {
    MASTER("최고 관리자", "ROLE_MASTER"),
    ADMIN("관리자", "ROLE_ADMIN"),
    MANAGER("매니저", "ROLE_MANAGER");

    private final String title;
    private final String value;

    AdminRole(String title, String value) {
        this.title = title;
        this.value = value;
    }
}
