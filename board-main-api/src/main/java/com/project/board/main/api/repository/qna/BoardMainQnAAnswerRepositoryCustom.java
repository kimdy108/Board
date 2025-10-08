package com.project.board.main.api.repository.qna;

import com.project.board.main.api.domain.qna.BoardMainQnA;
import com.project.board.main.api.dto.qna.answer.QnAAnswerList;

import java.util.List;

public interface BoardMainQnAAnswerRepositoryCustom  {
    List<QnAAnswerList> findQnAAnswerLists(BoardMainQnA boardMainQnA);
}
