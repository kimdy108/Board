package com.project.board.main.api.dto.announce;

import com.project.board.main.api.domain.announce.BoardMainAnnounce;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnounceInfo {
    private UUID announceUUID;
    private String announceTitle;
    private String announceContent;
    private int viewCounter;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;

    public static AnnounceInfo create(BoardMainAnnounce boardMainAnnounce) {
        AnnounceInfo announceInfo = new AnnounceInfo();
        announceInfo.setAnnounceUUID(boardMainAnnounce.getAnnounceUUID());
        announceInfo.setAnnounceTitle(boardMainAnnounce.getAnnounceTitle());
        announceInfo.setAnnounceContent(boardMainAnnounce.getAnnounceContent());
        announceInfo.setViewCounter(boardMainAnnounce.getViewCounter());
        announceInfo.setInsertDate(boardMainAnnounce.getInsertDate());
        announceInfo.setUpdateDate(boardMainAnnounce.getUpdateDate());
        return announceInfo;
    }
}
