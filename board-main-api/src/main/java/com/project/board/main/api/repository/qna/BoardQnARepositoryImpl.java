package com.project.board.main.api.repository.qna;

import com.project.board.main.api.domain.member.QBoardMember;
import com.project.board.main.api.domain.qna.BoardQnA;
import com.project.board.main.api.domain.qna.QBoardQnA;
import com.project.board.main.api.dto.qna.BoardQnAList;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardQnARepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardQnARepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(BoardQnA.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardQnA qboardQnA = QBoardQnA.boardQnA;
    QBoardMember qboardMember = QBoardMember.boardMember;

    public BoardQnAList findBoardQnA(String qnaGuid) {
        return jpaQueryFactory
                .select(Projections.fields(
                        BoardQnAList.class,
                        qboardQnA.qnaGuid.as("qnaGuid"),
                        qboardQnA.qnaTitle.as("qnaTitle"),
                        qboardQnA.qnaContent.as("qnaContent"),
                        qboardMember.memberGuid.as("memberGuid"),
                        qboardMember.memberNickName.as("memberNickName"),
                        qboardQnA.insertDate.as("qnaInsertDate")
                ))
                .from(qboardQnA)
                .innerJoin(qboardMember).on(qboardQnA.memberGuid.eq(qboardMember.memberGuid))
                .where(qboardQnA.qnaGuid.eq(qnaGuid))
                .fetchOne();
    }

    public List<BoardQnAList> findBoardQnAListAll() {
        return jpaQueryFactory
                .select(Projections.fields(
                        BoardQnAList.class,
                        qboardQnA.qnaGuid.as("qnaGuid"),
                        qboardQnA.qnaTitle.as("qnaTitle"),
                        qboardQnA.qnaContent.as("qnaContent"),
                        qboardMember.memberGuid.as("memberGuid"),
                        qboardMember.memberNickName.as("memberNickName"),
                        qboardQnA.insertDate.as("qnaInsertDate")
                ))
                .from(qboardQnA)
                .innerJoin(qboardMember).on(qboardQnA.memberGuid.eq(qboardMember.memberGuid))
                .where(qboardQnA.useFlag.eq(true))
                .orderBy(qboardQnA.insertDate.desc())
                .fetch();
    }
}
