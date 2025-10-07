package com.project.board.main.api.repository.post;

import com.project.board.main.api.domain.member.QBoardMainMember;
import com.project.board.main.api.domain.post.BoardMainPost;
import com.project.board.main.api.domain.post.QBoardMainPostComment;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.post.comment.PostCommentList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.board.main.api.utils.Common.decryptString;

@Repository
public class BoardMainPostCommentRepositoryImpl implements BoardMainPostCommentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardMainPostCommentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardMainPostComment qBoardMainPostComment = QBoardMainPostComment.boardMainPostComment;
    QBoardMainMember qBoardMainMember = QBoardMainMember.boardMainMember;

    @Override
    public List<PostCommentList> findPostCommentListByBoardMainPost(BoardMainPost boardMainPost) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qBoardMainPostComment.boardMainPost.eq(boardMainPost));
        bb.and(qBoardMainPostComment.isActive.eq(IsYesNo.YES));

        List<PostCommentList> postCommentLists = jpaQueryFactory
                .select(Projections.fields(
                        PostCommentList.class,
                        qBoardMainPostComment.commentUUID.as("commentUUID"),
                        qBoardMainMember.memberUUID.as("memberUUID"),
                        qBoardMainMember.memberNickName.as("memberNickName"),
                        qBoardMainPostComment.commentContent.as("commentContent"),
                        qBoardMainPostComment.isEncrypt.as("isEncrypt"),
                        qBoardMainPostComment.insertDate.as("insertDate"),
                        qBoardMainPostComment.updateDate.as("updateDate")
                ))
                .from(qBoardMainPostComment)
                .leftJoin(qBoardMainMember).on(qBoardMainPostComment.boardMainMember.eq(qBoardMainMember))
                .where(bb)
                .orderBy(qBoardMainPostComment.seq.asc())
                .fetch();

        for (PostCommentList postCommentList : postCommentLists) {
            postCommentList.setMemberNickName(decryptString(postCommentList.getMemberNickName()));
        }

        return postCommentLists;
    }
}
