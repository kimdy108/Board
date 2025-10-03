package com.project.board.main.api.dto.board.development;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevelopmentList {
    private UUID developmentUUID;
    private String developmentTitle;
    private String authorName;
    private int viewCounter;
    private LocalDateTime insertDate;
}
