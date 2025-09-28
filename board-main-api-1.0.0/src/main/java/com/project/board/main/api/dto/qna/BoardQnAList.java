package com.project.board.main.api.dto.qna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardQnAList {
    private String qnaGuid;
    private String qnaTitle;
    private String qnaContent;
    private String memberGuid;
    private String memberNickName;
    private LocalDateTime qnaInsertDate;
}
