package com.project.board.main.api.dto.announce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnounceList {
    private UUID announceUUID;
    private String announceTitle;
    private int viewCount;
    private LocalDateTime insertDate;
}
