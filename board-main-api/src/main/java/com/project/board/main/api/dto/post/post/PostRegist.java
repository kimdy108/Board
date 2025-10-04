package com.project.board.main.api.dto.post.post;

import com.project.board.main.api.dto.constant.post.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRegist {
    private String postTitle;
    private String postContent;
    private PostType postType;
}
