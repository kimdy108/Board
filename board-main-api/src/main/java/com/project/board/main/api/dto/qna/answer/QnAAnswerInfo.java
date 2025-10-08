package com.project.board.main.api.dto.qna.answer;

import com.project.board.main.api.domain.qna.BoardMainQnAAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnAAnswerInfo {
    private String answerContent;

    public static QnAAnswerInfo create(BoardMainQnAAnswer boardMainQnAAnswer) {
        QnAAnswerInfo answerInfo = new QnAAnswerInfo();
        answerInfo.setAnswerContent(boardMainQnAAnswer.getAnswerContent());
        return answerInfo;
    }
}
