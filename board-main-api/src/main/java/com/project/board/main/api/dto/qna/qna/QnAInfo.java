package com.project.board.main.api.dto.qna.qna;

import com.project.board.main.api.domain.qna.BoardMainQnA;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.qna.answer.QnAAnswerList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.project.board.main.api.utils.Common.encryptStringSalt;
import static com.project.board.main.api.utils.Common.decryptString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnAInfo {
    private String qnaTitle;
    private String qnaContent;
    private IsYesNo qnaAnswer;
    private String memberNickName;
    private UUID memberUUID;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
    private List<QnAAnswerList> qnaAnswerLists;

    public static QnAInfo create(BoardMainQnA boardMainQnA, List<QnAAnswerList> qnaAnswerLists) {
        QnAInfo qnaInfo = new QnAInfo();
        qnaInfo.setQnaTitle(boardMainQnA.getQnaTitle());
        qnaInfo.setQnaContent(boardMainQnA.getQnaContent());
        qnaInfo.setQnaAnswer(boardMainQnA.getIsAnswer());
        qnaInfo.setMemberNickName(boardMainQnA.getBoardMainMember().getMemberNickName());
        qnaInfo.setMemberUUID(boardMainQnA.getBoardMainMember().getMemberUUID());
        qnaInfo.setInsertDate(boardMainQnA.getInsertDate());
        qnaInfo.setUpdateDate(boardMainQnA.getUpdateDate());
        qnaInfo.setQnaAnswerLists(qnaAnswerLists);
        return qnaInfo;
    }
}
