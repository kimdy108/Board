package com.project.board.main.api.repository.board;

import com.project.board.main.api.domain.board.BoardFreeComment;
import com.project.board.main.api.domain.board.QBoardFreeComment;
import com.project.board.main.api.domain.member.QBoardMember;
import com.project.board.main.api.dto.board.BoardComment;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardFreeCommentRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardFreeCommentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(BoardFreeComment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardFreeComment qboardFreeComment = QBoardFreeComment.boardFreeComment;
    QBoardMember qboardMember = QBoardMember.boardMember;

    public List<BoardComment> getCommentAll(String freeGuid) {
        return jpaQueryFactory
                .select(Projections.fields(
                        BoardComment.class,
                        qboardFreeComment.freeGuid.as("boardGuid"),
                        qboardFreeComment.commentGuid.as("boardCommentGuid"),
                        qboardFreeComment.comment.as("boardComment"),
                        qboardFreeComment.memberGuid.as("boardCommentMemberGuid"),
                        qboardMember.memberNickName.as("boardCommentMemberNickName"),
                        qboardFreeComment.insertDate.as("boardCommentDate")
                ))
                .from(qboardFreeComment)
                .innerJoin(qboardMember).on(qboardFreeComment.memberGuid.eq(qboardMember.memberGuid))
                .where(qboardFreeComment.freeGuid.eq(freeGuid).and(qboardFreeComment.useFlag.eq(true)))
                .orderBy(qboardFreeComment.insertDate.asc())
                .fetch();
    }
}
