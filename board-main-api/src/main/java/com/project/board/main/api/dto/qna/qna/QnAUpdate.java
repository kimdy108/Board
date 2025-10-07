package com.project.board.main.api.dto.qna.qna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnAUpdate {
    private UUID qnaUUID;
    private String qnaTitle;
    private String qnaContent;
}
