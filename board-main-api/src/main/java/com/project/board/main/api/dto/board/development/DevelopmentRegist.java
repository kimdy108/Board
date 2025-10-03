package com.project.board.main.api.dto.board.development;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevelopmentRegist {
    private String developmentTitle;
    private String developmentContent;
}
