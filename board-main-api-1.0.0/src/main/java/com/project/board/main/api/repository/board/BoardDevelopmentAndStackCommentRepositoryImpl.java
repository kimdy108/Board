package com.project.board.main.api.repository.board;

import com.project.board.main.api.domain.board.BoardDevelopmentAndStackComment;
import com.project.board.main.api.domain.board.QBoardDevelopmentAndStackComment;
import com.project.board.main.api.domain.member.QBoardMember;
import com.project.board.main.api.dto.board.BoardComment;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDevelopmentAndStackCommentRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardDevelopmentAndStackCommentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(BoardDevelopmentAndStackComment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardDevelopmentAndStackComment qboardDevelopmentAndStackComment = QBoardDevelopmentAndStackComment.boardDevelopmentAndStackComment;
    QBoardMember qboardMember = QBoardMember.boardMember;

    public List<BoardComment> getCommentAll(String developmentStackGuid) {
        return jpaQueryFactory
                .select(Projections.fields(
                        BoardComment.class,
                        qboardDevelopmentAndStackComment.developmentStackGuid.as("boardGuid"),
                        qboardDevelopmentAndStackComment.commentGuid.as("boardCommentGuid"),
                        qboardDevelopmentAndStackComment.comment.as("boardComment"),
                        qboardDevelopmentAndStackComment.memberGuid.as("boardCommentMemberGuid"),
                        qboardMember.memberNickName.as("boardCommentMemberNickName"),
                        qboardDevelopmentAndStackComment.insertDate.as("boardCommentDate")
                ))
                .from(qboardDevelopmentAndStackComment)
                .innerJoin(qboardMember).on(qboardDevelopmentAndStackComment.memberGuid.eq(qboardMember.memberGuid))
                .where(qboardDevelopmentAndStackComment.developmentStackGuid.eq(developmentStackGuid).and(qboardDevelopmentAndStackComment.useFlag.eq(true)))
                .orderBy(qboardDevelopmentAndStackComment.insertDate.asc())
                .fetch();
    }
}
