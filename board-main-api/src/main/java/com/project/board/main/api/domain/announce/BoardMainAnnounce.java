package com.project.board.main.api.domain.announce;

import com.project.board.main.api.domain.admin.BoardMainAdmin;
import com.project.board.main.api.domain.common.BoardCommonBase;
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
        @Index(name = "idx_admin_seq", columnList = "admin_seq")
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

    @Comment("관리자 SEQ")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "admin_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BoardMainAdmin boardMainAdmin;

    @Builder
    public BoardMainAnnounce(UUID announceUUID, String announceTitle, String announceContent, BoardMainAdmin boardMainAdmin, IsYesNo isActive, LocalDateTime insertDate, LocalDateTime updateDate, String descriptionNote) {
        this.announceUUID = announceUUID;
        this.announceTitle = announceTitle;
        this.announceContent = announceContent;
        this.boardMainAdmin = boardMainAdmin;

        this.setIsActive(isActive);
        this.setInsertDate(insertDate);
        this.setUpdateDate(updateDate);
        this.setDescriptionNote(descriptionNote);
    }
}
