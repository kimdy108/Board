package com.project.board.main.api.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdate {
    private String boardGuid;
    private String boardTitle;
    private String boardContent;
}
