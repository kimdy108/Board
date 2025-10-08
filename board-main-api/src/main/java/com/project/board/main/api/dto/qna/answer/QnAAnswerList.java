package com.project.board.main.api.dto.qna.answer;

import com.project.board.main.api.dto.constant.common.IsYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnAAnswerList {
    private UUID answerUUID;
    private String answerContent;
    private IsYesNo isActive;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
}
