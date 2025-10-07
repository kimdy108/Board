package com.project.board.main.api.dto.qna.qna;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class QnAListPage {
    private List<QnAList> qnaContents;
    private Long totalCount;

    @Builder
    public QnAListPage(List<QnAList> qnaContents, Long totalCount) {
        this.qnaContents = qnaContents;
        this.totalCount = totalCount;
    }
}
