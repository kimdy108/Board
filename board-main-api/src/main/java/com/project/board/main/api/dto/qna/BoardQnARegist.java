package com.project.board.main.api.dto.qna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardQnARegist {
    private String qnaTitle;
    private String qnaContent;
}
