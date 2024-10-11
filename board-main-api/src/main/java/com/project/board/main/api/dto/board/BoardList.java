package com.project.board.main.api.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardList {
    private String boardGuid;
    private String boardTitle;
    private String boardContent;
    private String memberGuid;
    private String memberNickName;
    private LocalDateTime boardInsertDate;
}
