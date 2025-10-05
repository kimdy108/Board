package com.project.board.main.api.repository.post;

import com.project.board.main.api.domain.post.BoardMainPost;
import com.project.board.main.api.dto.post.comment.PostCommentList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMainPostCommentRepositoryCustom {
    List<PostCommentList> findPostCommentListByBoardMainPost(BoardMainPost boardMainPost);
}
