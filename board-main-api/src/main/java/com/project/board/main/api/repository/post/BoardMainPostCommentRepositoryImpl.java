package com.project.board.main.api.repository.post;

import com.project.board.main.api.domain.post.QBoardMainPostComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BoardMainPostCommentRepositoryImpl implements BoardMainPostCommentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardMainPostCommentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardMainPostComment qBoardMainPostComment = QBoardMainPostComment.boardMainPostComment;
}
