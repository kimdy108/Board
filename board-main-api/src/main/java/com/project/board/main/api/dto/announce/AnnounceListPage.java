package com.project.board.main.api.dto.announce;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AnnounceListPage {
    private List<AnnounceList> announceContents;
    private Long totalCount;

    @Builder
    public AnnounceListPage(List<AnnounceList> announceContents, Long totalCount){
        this.announceContents = announceContents;
        this.totalCount = totalCount;
    }
}
