package com.project.board.main.api.domain.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_development_stack_comment", indexes = {
        @Index(name = "idx_seq", columnList = "seq"),
        @Index(name = "idx_development_stack_guid", columnList = "development_stack_guid"),
        @Index(name = "idx_member_guid", columnList = "member_guid")
})
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDevelopmentAndStackComment {
    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name = "comment_guid")
    private String commentGuid;

    @Column(name = "comment")
    private String comment;

    @Column(name = "development_stack_guid")
    private String developmentStackGuid;

    @Column(name = "member_guid")
    private String memberGuid;

    @Column(name = "use_flag")
    private boolean useFlag;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    public static BoardDevelopmentAndStackComment regist(String commentGuid,
                                                         String comment,
                                                         String developmentStackGuid,
                                                         String memberGuid) {
        BoardDevelopmentAndStackComment boardDevelopmentAndStackComment = new BoardDevelopmentAndStackComment();
        boardDevelopmentAndStackComment.setCommentGuid(commentGuid);
        boardDevelopmentAndStackComment.setComment(comment);
        boardDevelopmentAndStackComment.setDevelopmentStackGuid(developmentStackGuid);
        boardDevelopmentAndStackComment.setMemberGuid(memberGuid);
        boardDevelopmentAndStackComment.setUseFlag(true);
        boardDevelopmentAndStackComment.setInsertDate(LocalDateTime.now());
        boardDevelopmentAndStackComment.setUpdateDate(LocalDateTime.now());
        return boardDevelopmentAndStackComment;
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
