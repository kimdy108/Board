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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "board_main_development", indexes = {
        @Index(name = "idx_development_uuid", columnList = "development_uuid"),
        @Index(name = "idx_development_title", columnList = "development_title"),
        @Index(name = "idx_author_seq", columnList = "author_seq")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMainDevelopment extends BoardCommonBase {
    @Comment("개발 게시판 UUID")
    @Column(name = "development_uuid", length = 50, unique = true, nullable = false, updatable = false)
    private UUID developmentUUID;

    @Comment("개발 게시판 제목")
    @Column(name = "development_title", length = 100, nullable = false)
    private String developmentTitle;

    @Comment("개발 게시판 내용")
    @Column(name = "development_content", columnDefinition = "TEXT")
    private String developmentContent;

    @Comment("조회 수")
    @Column(name = "view_counter", length = 10, nullable = false)
    private int viewCounter;

    @Comment("작성자 SEQ")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BoardMainMember boardMainMember;

    @OneToMany(fetch = LAZY, mappedBy = "boardMainDevelopment")
    private List<BoardMainDevelopmentComment> boardMainDevelopmentCommentList = new ArrayList<>();

    @Builder
    public BoardMainDevelopment (String developmentTitle, String developmentContent, BoardMainMember boardMainMember) {
        this.developmentUUID = UUID.randomUUID();
        this.developmentTitle = developmentTitle;
        this.developmentContent = developmentContent;
        this.viewCounter = 0;
        this.boardMainMember = boardMainMember;

        this.setIsActive(IsYesNo.YES);
        this.setInsertDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
    }

    public void update(String developmentTitle, String developmentContent) {
        this.developmentTitle = developmentTitle;
        this.developmentContent = developmentContent;

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateStatus(IsYesNo isActive) {
        this.setIsActive(isActive);
        this.setUpdateDate(LocalDateTime.now());
    }

    public void addViewCounter() {
        this.viewCounter++;
    }
}
