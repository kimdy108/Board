package com.project.board.main.api.dto.post.post;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PostListPage {
    private List<PostList> postContents;
    private Long totalCount;

    @Builder
    public PostListPage(List<PostList> postContents, Long totalCount) {
        this.postContents = postContents;
        this.totalCount = totalCount;
    }
}
