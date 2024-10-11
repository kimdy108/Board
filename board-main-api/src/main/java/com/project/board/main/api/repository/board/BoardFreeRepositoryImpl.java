package com.project.board.main.api.repository.board;

import com.project.board.main.api.domain.board.BoardFree;
import com.project.board.main.api.domain.board.QBoardFree;
import com.project.board.main.api.domain.member.QBoardMember;
import com.project.board.main.api.dto.announce.BoardNotice;
import com.project.board.main.api.dto.board.BoardList;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardFreeRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardFreeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(BoardFree.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardFree qboardFree = QBoardFree.boardFree;
    QBoardMember qboardMember = QBoardMember.boardMember;

    public BoardList findBoardFree(String boardGuid) {
        return jpaQueryFactory
                .select(Projections.fields(
                        BoardList.class,
                        qboardFree.freeGuid.as("boardGuid"),
                        qboardFree.freeTitle.as("boardTitle"),
                        qboardFree.freeContent.as("boardContent"),
                        qboardMember.memberGuid.as("memberGuid"),
                        qboardMember.memberNickName.as("memberNickName"),
                        qboardFree.insertDate.as("boardInsertDate")
                ))
                .from(qboardFree)
                .innerJoin(qboardMember).on(qboardFree.memberGuid.eq(qboardMember.memberGuid))
                .where(qboardFree.freeGuid.eq(boardGuid))
                .orderBy(qboardFree.insertDate.asc())
                .fetchOne();
    }

    public List<BoardList> findBoardFreeListAll() {
        return jpaQueryFactory
                .select(Projections.fields(
                        BoardList.class,
                        qboardFree.freeGuid.as("boardGuid"),
                        qboardFree.freeTitle.as("boardTitle"),
                        qboardFree.freeContent.as("boardContent"),
                        qboardMember.memberGuid.as("memberGuid"),
                        qboardMember.memberNickName.as("memberNickName"),
                        qboardFree.insertDate.as("boardInsertDate")
                ))
                .from(qboardFree)
                .innerJoin(qboardMember).on(qboardFree.memberGuid.eq(qboardMember.memberGuid))
                .where(qboardFree.useFlag.eq(true))
                .orderBy(qboardFree.insertDate.asc())
                .fetch();
    }
}
