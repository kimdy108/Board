package com.project.board.main.api.domain.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_free_comment", indexes = {
        @Index(name = "idx_seq", columnList = "seq"),
        @Index(name = "idx_free_guid", columnList = "free_guid"),
        @Index(name = "idx_member_guid", columnList = "member_guid")
})
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardFreeComment {
    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name = "comment_guid")
    private String commentGuid;

    @Column(name = "comment")
    private String comment;

    @Column(name = "free_guid")
    private String freeGuid;

    @Column(name = "member_guid")
    private String memberGuid;

    @Column(name = "use_flag")
    private boolean useFlag;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    public static BoardFreeComment regist(String commentGuid,
                                          String comment,
                                          String freeGuid,
                                          String memberGuid) {
        BoardFreeComment boardFreeComment = new BoardFreeComment();
        boardFreeComment.setCommentGuid(commentGuid);
        boardFreeComment.setComment(comment);
        boardFreeComment.setFreeGuid(freeGuid);
        boardFreeComment.setMemberGuid(memberGuid);
        boardFreeComment.setUseFlag(true);
        boardFreeComment.setUpdateDate(LocalDateTime.now());
        boardFreeComment.setInsertDate(LocalDateTime.now());
        return boardFreeComment;
    }

    public void update(String commentString) {
        this.comment = commentString;
        this.updateDate = LocalDateTime.now();
    }

    public void updateUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
        this.updateDate = LocalDateTime.now();
    }
}
