package com.project.board.main.api.repository.post;

import com.project.board.main.api.dto.constant.post.PostType;
import com.project.board.main.api.dto.post.post.PostListPage;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMainPostRepositoryCustom {
    PostListPage findBoardMainPostListPage(String searchType, String searchValue, PostType postType);
}
