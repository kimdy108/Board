package com.project.board.main.api.dto.member.user;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserListPage {
    private List<UserList> userContents;
    private Long totalCount;

    @Builder
    public UserListPage (List<UserList> userContents, Long totalCount) {
        this.userContents = userContents;
        this.totalCount = totalCount;
    }
}
