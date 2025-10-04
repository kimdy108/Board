package com.project.board.main.api.dto.post.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostList {
    private UUID postUUID;
    private String postTitle;
    private String memberName;
    private int viewCount;
    private LocalDateTime insertDate;
}
