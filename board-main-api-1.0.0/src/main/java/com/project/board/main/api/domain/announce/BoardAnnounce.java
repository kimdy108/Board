package com.project.board.main.api.domain.announce;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_announce", indexes = {
        @Index(name = "idx_seq", columnList = "seq"),
        @Index(name = "idx_announce_title", columnList = "announce_title")
})
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardAnnounce {
    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name = "member_guid")
    private String memberGuid;

    @Column(name = "annouce_guid")
    private String annouceGuid;

    @Column(name = "announce_title")
    private String announceTitle;

    @Column(name = "announce_content")
    private String announceContent;

    @Column(name = "use_flag")
    private boolean useFlag;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    public static BoardAnnounce regist(String memberGuid,
                                       String announceGuid,
                                       String announceTitle,
                                       String announceContent) {
        BoardAnnounce boardAnnounce = new BoardAnnounce();
        boardAnnounce.setAnnouceGuid(announceGuid);
        boardAnnounce.setMemberGuid(memberGuid);
        boardAnnounce.setAnnounceTitle(announceTitle);
        boardAnnounce.setAnnounceContent(announceContent);
        boardAnnounce.setUseFlag(true);
        boardAnnounce.setInsertDate(LocalDateTime.now());
        boardAnnounce.setUpdateDate(LocalDateTime.now());
        return boardAnnounce;
    }

    public void update(String announceTitle,
                       String announceContent) {
        this.announceTitle = announceTitle;
        this.announceContent = announceContent;
        this.updateDate = LocalDateTime.now();
    }

    public void updateUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
        this.updateDate = LocalDateTime.now();
    }
}
