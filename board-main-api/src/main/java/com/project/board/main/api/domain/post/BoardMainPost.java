package com.project.board.main.api.domain.post;

import com.project.board.main.api.domain.common.BoardCommonBase;
import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.constant.post.PostType;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "board_main_post", indexes = {
        @Index(name = "idx_post_uuid", columnList = "post_uuid"),
        @Index(name = "idx_post_title", columnList = "post_title"),
        @Index(name = "idx_post_type", columnList = "post_type"),
        @Index(name = "idx_author_seq", columnList = "author_seq")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMainPost extends BoardCommonBase {
    @Comment("게시글 UUID")
    @Column(name = "post_uuid", length = 50, unique = true, nullable = false, updatable = false)
    private UUID postUUID;

    @Comment("게시글 제목")
    @Column(name = "post_title", length = 100, nullable = false)
    private String postTitle;

    @Comment("게시글 내용")
    @Column(name = "post_content", columnDefinition = "TEXT")
    private String postContent;

    @Comment("게시글 타입")
    @Column(name = "post_type", columnDefinition = "ENUM('DEVELOPMENT','STACK','FREE') NOT NULL DEFAULT 'FREE'")
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Comment("조회 수")
    @Column(name = "view_count", length = 10, nullable = false)
    private int viewCount;

    @Comment("작성자 SEQ")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BoardMainMember boardMainMember;

    @OneToMany(fetch = LAZY, mappedBy = "boardMainPost")
    private List<BoardMainPostComment> boardMainPostCommentList = new ArrayList<>();

    @Builder
    public BoardMainPost(String postTitle, String postContent, PostType postType, BoardMainMember boardMainMember) {
        this.postUUID = UUID.randomUUID();
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postType = postType;
        this.viewCount = 0;
        this.boardMainMember = boardMainMember;

        this.setIsActive(IsYesNo.YES);
        this.setInsertDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
    }

    public void update(String postTitle, String postContent) {
        this.postTitle = postTitle;
        this.postContent = postContent;

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateStatus(IsYesNo isActive) {
        this.setIsActive(isActive);
        this.setUpdateDate(LocalDateTime.now());
    }

    public void addViewCount() {
        this.viewCount++;
    }
}
