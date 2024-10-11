package com.project.board.main.api.domain.qna;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_qna", indexes = {
        @Index(name = "idx_seq", columnList = "seq"),
        @Index(name = "idx_qna_title", columnList = "qna_title"),
        @Index(name = "idx_member_guid", columnList = "member_guid")
})
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardQnA {
    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name = "member_guid")
    private String memberGuid;

    @Column(name = "qna_guid")
    private String qnaGuid;

    @Column(name = "qna_title")
    private String qnaTitle;

    @Column(name = "qna_content")
    private String qnaContent;

    @Column(name = "use_flag")
    private boolean useFlag;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    public static BoardQnA regist (String memberGuid,
                                   String qnaGuid,
                                   String qnaTitle,
                                   String qnaContent) {
        BoardQnA boardQna = new BoardQnA();
        boardQna.setMemberGuid(memberGuid);
        boardQna.setQnaGuid(qnaGuid);
        boardQna.setQnaTitle(qnaTitle);
        boardQna.setQnaContent(qnaContent);
        boardQna.setUseFlag(true);
        boardQna.setUpdateDate(LocalDateTime.now());
        boardQna.setInsertDate(LocalDateTime.now());
        return boardQna;
    }

    public void update(String qnaTitle,
                       String qnaContent) {
        this.qnaTitle = qnaTitle;
        this.qnaContent = qnaContent;
        this.updateDate = LocalDateTime.now();
    }

    public void updateUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
        this.updateDate = LocalDateTime.now();
    }
}
