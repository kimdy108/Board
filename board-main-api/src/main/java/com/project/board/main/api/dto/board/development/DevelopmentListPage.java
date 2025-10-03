package com.project.board.main.api.dto.board.development;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DevelopmentListPage {
    private List<DevelopmentList> developmentContents;
    private Long totalCount;

    @Builder
    public DevelopmentListPage(List<DevelopmentList> developmentContents, Long totalCount){
        this.developmentContents = developmentContents;
        this.totalCount = totalCount;
    }
}
