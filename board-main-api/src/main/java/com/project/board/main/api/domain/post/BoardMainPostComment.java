package com.project.board.main.api.domain.post;

import com.project.board.main.api.domain.common.BoardCommonBase;
import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "board_main_post_comment", indexes = {
        @Index(name = "idx_comment_uuid", columnList = "comment_uuid"),
        @Index(name = "idx_author_seq", columnList = "author_seq"),
        @Index(name = "idx_post_seq", columnList = "post_seq")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMainPostComment extends BoardCommonBase {
    @Comment("댓글 UUID")
    @Column(name = "comment_uuid", length = 50, nullable = false, unique = true, updatable = false)
    private UUID commentUUID;

    @Comment("댓글 내용")
    @Column(name = "comment_content", length = 100, nullable = false)
    private String commentContent;

    @Comment("작성자 SEQ")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BoardMainMember boardMainMember;

    @Comment("게시글 SEQ")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BoardMainPost boardMainPost;

    @Comment("비밀글 여부")
    @Column(name = "is_encrypt", columnDefinition = "ENUM('YES','NO') NOT NULL DEFAULT 'NO'")
    @Enumerated(EnumType.STRING)
    private IsYesNo isEncrypt;

    @Builder
    public BoardMainPostComment(String commentContent, BoardMainMember boardMainMember, BoardMainPost boardMainPost, IsYesNo isEncrypt) {
        this.commentUUID = UUID.randomUUID();
        this.commentContent = commentContent;
        this.boardMainMember = boardMainMember;
        this.boardMainPost = boardMainPost;
        this.isEncrypt = isEncrypt;

        this.setIsActive(IsYesNo.YES);
        this.setInsertDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
    }

    public void update(String commentContent, IsYesNo isEncrypt) {
        this.commentContent = commentContent;
        this.isEncrypt = isEncrypt;

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateStatus(IsYesNo isActive) {
        this.setIsActive(isActive);
        this.setUpdateDate(LocalDateTime.now());
    }
}
