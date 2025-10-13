package com.project.board.main.api.domain.announce;

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
@Table(name = "board_main_announce", indexes = {
        @Index(name = "idx_announce_uuid", columnList = "announce_uuid"),
        @Index(name = "idx_announce_title", columnList = "announce_title"),
        @Index(name = "idx_author_seq", columnList = "author_seq")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMainAnnounce extends BoardCommonBase {
    @Comment("공지사항 UUID")
    @Column(name = "announce_uuid", length = 50, unique = true, nullable = false, updatable = false)
    private UUID announceUUID;

    @Comment("공지사항 제목")
    @Column(name = "announce_title", length = 100, nullable = false)
    private String announceTitle;

    @Comment("공지사항 내용")
    @Column(name = "announce_content", columnDefinition = "TEXT")
    private String announceContent;

    @Comment("조회 수")
    @Column(name = "view_count", length = 10, nullable = false)
    private int viewCount;

    @Comment("작성자 SEQ (관리자)")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BoardMainMember boardMainMember;

    @Builder
    public BoardMainAnnounce(UUID announceUUID, String announceTitle, String announceContent, BoardMainMember boardMainMember) {
        this.announceUUID = announceUUID;
        this.announceTitle = announceTitle;
        this.announceContent = announceContent;
        this.boardMainMember = boardMainMember;
        this.viewCount = 0;

        this.setIsActive(IsYesNo.YES);
        this.setInsertDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
    }

    public void update(String announceTitle, String announceContent) {
        this.announceTitle = announceTitle;
        this.announceContent = announceContent;

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
