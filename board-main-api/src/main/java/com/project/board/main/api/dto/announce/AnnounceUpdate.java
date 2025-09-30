package com.project.board.main.api.dto.announce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnounceUpdate {
    private UUID announceUUID;
    private String announceTitle;
    private String announceContent;
}
