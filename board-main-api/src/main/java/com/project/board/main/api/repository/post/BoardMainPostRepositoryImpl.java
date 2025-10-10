package com.project.board.main.api.repository.post;

import com.project.board.main.api.domain.post.QBoardMainPost;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.post.PostType;
import com.project.board.main.api.dto.post.post.PostList;
import com.project.board.main.api.dto.post.post.PostListPage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.board.main.api.utils.Common.decryptString;
import static com.project.board.main.api.utils.Common.encryptString;

@Repository
public class BoardMainPostRepositoryImpl implements BoardMainPostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardMainPostRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardMainPost qBoardMainPost = QBoardMainPost.boardMainPost;

    @Override
    public PostListPage findBoardMainPostListPage(String searchType, String searchValue, PostType postType) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qBoardMainPost.postType.eq(postType));
        bb.and(qBoardMainPost.isActive.eq(IsYesNo.YES));

        List<PostList> postLists = jpaQueryFactory
                .select(Projections.fields(
                        PostList.class,
                        qBoardMainPost.postUUID.as("postUUID"),
                        qBoardMainPost.postTitle.as("postTitle"),
                        qBoardMainPost.boardMainMember.memberNickName.as("memberNickName"),
                        qBoardMainPost.viewCount.as("viewCount"),
                        qBoardMainPost.insertDate.as("insertDate")
                ))
                .from(qBoardMainPost)
                .where(bb, eqPostTitle(searchType, searchValue), eqPostContent(searchType, searchValue), eqPostAuthor(searchType, searchValue))
                .orderBy(qBoardMainPost.seq.desc())
                .fetch();

        Long count = jpaQueryFactory
                .select(qBoardMainPost.seq.count())
                .from(qBoardMainPost)
                .where(bb, eqPostTitle(searchType, searchValue), eqPostContent(searchType, searchValue), eqPostAuthor(searchType, searchValue))
                .fetchOne();

        return PostListPage.builder()
                .postContents(postLists)
                .totalCount(count)
                .build();
    }

    private BooleanExpression eqPostTitle(String searchType, String searchValue) {
        if (!searchType.equals("postTitle") || "".equals(searchValue)) return null;
        return qBoardMainPost.postTitle.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqPostContent(String searchType, String searchValue) {
        if (!searchType.equals("postContent") || "".equals(searchValue)) return null;
        return qBoardMainPost.postContent.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqPostAuthor(String searchType, String searchValue) {
        if (!searchType.equals("postAuthor") || "".equals(searchValue)) return null;
        return qBoardMainPost.boardMainMember.memberNickName.eq(encryptString(searchValue));
    }
}
