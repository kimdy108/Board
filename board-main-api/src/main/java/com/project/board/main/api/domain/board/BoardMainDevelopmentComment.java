package com.project.board.main.api.domain.board;

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
@Table(name = "board_main_development_comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMainDevelopmentComment extends BoardCommonBase {
    @Comment("개발 게시판 댓글 UUID")
    @Column(name = "development_comment_uuid", length = 50, unique = true, nullable = false, updatable = false)
    private UUID developmentCommentUUID;

    @Comment("개발 게시판 댓글 내용")
    @Column(name = "development_comment_content", columnDefinition = "TEXT")
    private String developmentCommentContent;

    @Comment("작성자 SEQ")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BoardMainMember boardMainMember;

    @Comment("개발 게시판 SEQ")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "development_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BoardMainDevelopment boardMainDevelopment;

    @Builder
    public BoardMainDevelopmentComment(String developmentCommentContent, BoardMainMember boardMainMember, BoardMainDevelopment boardMainDevelopment) {
        this.developmentCommentUUID = UUID.randomUUID();
        this.developmentCommentContent = developmentCommentContent;
        this.boardMainMember = boardMainMember;
        this.boardMainDevelopment = boardMainDevelopment;

        this.setIsActive(IsYesNo.YES);
        this.setInsertDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
    }

    public void update(String developmentCommentContent) {
        this.developmentCommentContent = developmentCommentContent;

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateStatus(IsYesNo isActive) {
        this.setIsActive(isActive);
        this.setUpdateDate(LocalDateTime.now());
    }
}
