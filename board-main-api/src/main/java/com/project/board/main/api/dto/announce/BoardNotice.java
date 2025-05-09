package com.project.board.main.api.dto.announce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardNotice {
    private String noticeGuid;
    private String noticeTitle;
    private String noticeContent;
    private String memberGuid;
    private String memberNickName;
    private LocalDateTime noticeInsertDate;
}
