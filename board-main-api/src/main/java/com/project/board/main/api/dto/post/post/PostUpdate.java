package com.project.board.main.api.dto.post.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdate {
    private UUID postUUID;
    private String postTitle;
    private String postContent;
}
