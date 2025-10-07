package com.project.board.main.api.dto.qna.qna;

import com.project.board.main.api.dto.constant.common.IsYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnAList {
    private UUID qnaUUID;
    private String qnaTitle;
    private String memberNickName;
    private IsYesNo isAnswer;
    private LocalDateTime insertDate;
}
