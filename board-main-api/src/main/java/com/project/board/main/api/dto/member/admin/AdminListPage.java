package com.project.board.main.api.dto.member.admin;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AdminListPage {
    private List<AdminList> adminContents;
    private Long totalCount;

    @Builder
    public AdminListPage(List<AdminList> adminContents, Long totalCount) {
        this.adminContents = adminContents;
        this.totalCount = totalCount;
    }
}
