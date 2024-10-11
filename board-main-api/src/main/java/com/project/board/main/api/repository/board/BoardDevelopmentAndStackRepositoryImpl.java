package com.project.board.main.api.repository.board;

import com.project.board.main.api.domain.board.BoardDevelopmentAndStack;
import com.project.board.main.api.domain.board.QBoardDevelopmentAndStack;
import com.project.board.main.api.domain.member.QBoardMember;
import com.project.board.main.api.dto.board.BoardList;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDevelopmentAndStackRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardDevelopmentAndStackRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(BoardDevelopmentAndStack.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardDevelopmentAndStack qboardDevelopmentAndStack = QBoardDevelopmentAndStack.boardDevelopmentAndStack;
    QBoardMember qboardMember = QBoardMember.boardMember;

    public BoardList findBoardDevelopmentAndStack(String boardGuid) {
        return jpaQueryFactory
                .select(Projections.fields(
                        BoardList.class,
                        qboardDevelopmentAndStack.developmentStackGuid.as("boardGuid"),
                        qboardDevelopmentAndStack.developmentStackTitle.as("boardTitle"),
                        qboardDevelopmentAndStack.developmentStackContent.as("boardContent"),
                        qboardMember.memberGuid.as("memberGuid"),
                        qboardMember.memberNickName.as("memberNickName"),
                        qboardDevelopmentAndStack.insertDate.as("boardInsertDate")
                ))
                .from(qboardDevelopmentAndStack)
                .innerJoin(qboardMember).on(qboardDevelopmentAndStack.memberGuid.eq(qboardMember.memberGuid))
                .where(qboardDevelopmentAndStack.developmentStackGuid.eq(boardGuid))
                .fetchOne();
    }

    public List<BoardList> findBoardDevelopmentAndStackListAll() {
        return jpaQueryFactory
                .select(Projections.fields(
                        BoardList.class,
                        qboardDevelopmentAndStack.developmentStackGuid.as("boardGuid"),
                        qboardDevelopmentAndStack.developmentStackTitle.as("boardTitle"),
                        qboardDevelopmentAndStack.developmentStackContent.as("boardContent"),
                        qboardMember.memberGuid.as("memberGuid"),
                        qboardMember.memberNickName.as("memberNickName"),
                        qboardDevelopmentAndStack.insertDate.as("boardInsertDate")
                ))
                .from(qboardDevelopmentAndStack)
                .innerJoin(qboardMember).on(qboardDevelopmentAndStack.memberGuid.eq(qboardMember.memberGuid))
                .where(qboardDevelopmentAndStack.useFlag.eq(true))
                .orderBy(qboardDevelopmentAndStack.insertDate.desc())
                .fetch();
    }
}
