package com.project.board.main.api.dto.post.comment;

import com.project.board.main.api.dto.constant.common.IsYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentList {
    private UUID commentUUID;
    private UUID memberUUID;
    private String memberNickName;
    private String commentContent;
    private IsYesNo isEncrypt;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
}
