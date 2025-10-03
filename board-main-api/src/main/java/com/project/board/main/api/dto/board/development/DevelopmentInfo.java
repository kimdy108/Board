package com.project.board.main.api.dto.board.development;

import com.project.board.main.api.domain.board.BoardMainDevelopment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.board.main.api.utils.Common.encryptStringSalt;
import static com.project.board.main.api.utils.Common.initDecryptStr;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevelopmentInfo {
    private UUID developmentUUID;
    private String developmentTitle;
    private String developmentContent;
    private int viewCounter;
    private int commentCounter;
    private String memberName;
    private UUID memberUUID;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;

    public static DevelopmentInfo create(BoardMainDevelopment boardMainDevelopment) {
        DevelopmentInfo developmentInfo = new DevelopmentInfo();
        developmentInfo.setDevelopmentUUID(boardMainDevelopment.getDevelopmentUUID());
        developmentInfo.setDevelopmentTitle(boardMainDevelopment.getDevelopmentTitle());
        developmentInfo.setDevelopmentContent(boardMainDevelopment.getDevelopmentContent());
        developmentInfo.setViewCounter(boardMainDevelopment.getViewCounter());
        developmentInfo.setCommentCounter(boardMainDevelopment.getBoardMainDevelopmentCommentList().size());
        developmentInfo.setMemberName(encryptStringSalt(initDecryptStr(boardMainDevelopment.getBoardMainMember().getMemberName())));
        developmentInfo.setMemberUUID(boardMainDevelopment.getBoardMainMember().getMemberUUID());
        developmentInfo.setInsertDate(boardMainDevelopment.getInsertDate());
        developmentInfo.setUpdateDate(boardMainDevelopment.getUpdateDate());
        return developmentInfo;
    }
}
