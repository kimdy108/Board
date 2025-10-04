package com.project.board.main.api.dto.constant.post;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum PostType {
    DEVELOPMENT("개발"),
    STACK("스택"),
    FREE("자유");

    private final String value;

    PostType(String value) {
        this.value = value;
    }
}
