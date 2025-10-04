package com.project.board.main.api.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardComment {
    private String boardGuid;
    private String boardCommentGuid;
    private String boardComment;
    private String boardCommentMemberGuid;
    private String boardCommentMemberNickName;
    private LocalDateTime boardCommentDate;
}
