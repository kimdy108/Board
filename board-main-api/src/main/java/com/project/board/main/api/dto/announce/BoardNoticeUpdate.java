package com.project.board.main.api.dto.announce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardNoticeUpdate {
    private String noticeGuid;
    private String noticeTitle;
    private String noticeContent;
}
