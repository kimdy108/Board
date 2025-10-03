package com.project.board.main.api.dto.board.development;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevelopmentUpdate {
    private UUID developmentUUID;
    private String developmentTitle;
    private String developmentContent;
}
