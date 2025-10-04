package com.project.board.main.api.dto.post.comment;

import com.project.board.main.api.dto.constant.common.IsYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentUpdate {
    private UUID commentUUID;
    private String commentContent;
    private IsYesNo isEncrypt;
}
