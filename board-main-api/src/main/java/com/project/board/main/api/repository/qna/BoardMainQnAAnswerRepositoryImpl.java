package com.project.board.main.api.repository.qna;

import com.project.board.main.api.domain.qna.BoardMainQnA;
import com.project.board.main.api.domain.qna.QBoardMainQnAAnswer;
import com.project.board.main.api.dto.qna.answer.QnAAnswerList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardMainQnAAnswerRepositoryImpl implements BoardMainQnAAnswerRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardMainQnAAnswerRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardMainQnAAnswer qBoardMainQnAAnswer = QBoardMainQnAAnswer.boardMainQnAAnswer;

    @Override
    public List<QnAAnswerList> findQnAAnswerLists(BoardMainQnA boardMainQnA) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qBoardMainQnAAnswer.boardMainQnA.eq(boardMainQnA));

        return jpaQueryFactory
                .select(Projections.fields(
                        QnAAnswerList.class,
                        qBoardMainQnAAnswer.answerUUID.as("answerUUID"),
                        qBoardMainQnAAnswer.answerContent.as("answerContent"),
                        qBoardMainQnAAnswer.isActive.as("isActive"),
                        qBoardMainQnAAnswer.insertDate.as("insertDate"),
                        qBoardMainQnAAnswer.updateDate.as("updateDate")
                ))
                .from(qBoardMainQnAAnswer)
                .where(bb)
                .orderBy(qBoardMainQnAAnswer.seq.asc())
                .fetch();
    }
}
