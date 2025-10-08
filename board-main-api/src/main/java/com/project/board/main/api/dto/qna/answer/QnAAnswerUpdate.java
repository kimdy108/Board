package com.project.board.main.api.dto.qna.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnAAnswerUpdate {
    private UUID answerUUID;
    private String answerContent;
}
