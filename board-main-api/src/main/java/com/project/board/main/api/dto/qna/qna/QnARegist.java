package com.project.board.main.api.dto.qna.qna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnARegist {
    private String qnaTitle;
    private String qnaContent;
}
