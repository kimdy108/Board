package com.project.board.main.api.domain.qna;

import com.project.board.main.api.domain.common.BoardCommonBase;
import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "board_main_qna", indexes = {
        @Index(name = "idx_qna_uuid", columnList = "qna_uuid"),
        @Index(name = "idx_qna_title", columnList = "qna_title"),
        @Index(name = "idx_qna_content", columnList = "qna_content"),
        @Index(name = "idx_author_seq", columnList = "author_seq")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMainQnA extends BoardCommonBase {
    @Comment("QnA UUID")
    @Column(name = "qna_uuid", length = 50, unique = true, nullable = false, updatable = false)
    private UUID qnaUUID;

    @Comment("QnA 제목")
    @Column(name = "qna_title", length = 100, nullable = false)
    private String qnaTitle;

    @Comment("QnA 내용")
    @Column(name = "qna_content", columnDefinition = "TEXT")
    private String qnaContent;

    @Comment("작성자 SEQ")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BoardMainMember boardMainMember;

    @Comment("답변 여부")
    @Column(name = "is_answer", columnDefinition = "ENUM('YES','NO') NOT NULL DEFAULT 'NO'")
    @Enumerated(EnumType.STRING)
    private IsYesNo isAnswer;

    @Builder
    public BoardMainQnA(String qnaTitle, String qnaContent, BoardMainMember boardMainMember) {
        this.qnaUUID = UUID.randomUUID();
        this.qnaTitle = qnaTitle;
        this.qnaContent = qnaContent;
        this.boardMainMember = boardMainMember;
        this.isAnswer = IsYesNo.NO;

        this.setIsActive(IsYesNo.YES);
        this.setInsertDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
    }

    public void update(String qnaTitle, String qnaContent) {
        this.qnaTitle = qnaTitle;
        this.qnaContent = qnaContent;

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateStatus(IsYesNo isActive) {
        this.setIsActive(isActive);
        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateIsAnswer() {
        this.isAnswer = IsYesNo.YES;
    }
}
