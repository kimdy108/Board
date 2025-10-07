package com.project.board.main.api.dto.post.post;

import com.project.board.main.api.domain.post.BoardMainPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.board.main.api.utils.Common.encryptStringSalt;
import static com.project.board.main.api.utils.Common.decryptString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInfo {
    private String postTitle;
    private String postContent;
    private int viewCount;
    private String memberNickName;
    private UUID memberUUID;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;

    public static PostInfo create(BoardMainPost boardMainPost) {
        PostInfo postInfo = new PostInfo();
        postInfo.setPostTitle(boardMainPost.getPostTitle());
        postInfo.setPostContent(boardMainPost.getPostContent());
        postInfo.setViewCount(boardMainPost.getViewCount());
        postInfo.setMemberNickName(encryptStringSalt(decryptString(boardMainPost.getBoardMainMember().getMemberNickName())));
        postInfo.setMemberUUID(boardMainPost.getBoardMainMember().getMemberUUID());
        postInfo.setInsertDate(boardMainPost.getInsertDate());
        postInfo.setUpdateDate(boardMainPost.getUpdateDate());
        return postInfo;
    }
}
