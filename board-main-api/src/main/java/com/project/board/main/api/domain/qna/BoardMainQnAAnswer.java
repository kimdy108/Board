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
@Table(name = "board_main_qna_answer", indexes = {
        @Index(name = "idx_answer_uuid", columnList = "answer_uuid"),
        @Index(name = "idx_author_seq", columnList = "author_seq"),
        @Index(name = "idx_qna_seq", columnList = "qna_seq")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMainQnAAnswer extends BoardCommonBase {
    @Comment("답변 UUID")
    @Column(name = "answer_uuid", length = 50, unique = true, nullable = false, updatable = false)
    private UUID answerUUID;

    @Comment("답변 내용")
    @Column(name = "answer_content", columnDefinition = "TEXT")
    private String answerContent;

    @Comment("작성자 SEQ")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BoardMainMember boardMainMember;

    @Comment("QnA SEQ")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "qna_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BoardMainQnA boardMainQnA;

    @Builder
    public BoardMainQnAAnswer(String answerContent, BoardMainMember boardMainMember, BoardMainQnA boardMainQnA) {
        this.answerUUID = UUID.randomUUID();
        this.answerContent = answerContent;
        this.boardMainMember = boardMainMember;
        this.boardMainQnA = boardMainQnA;

        this.setIsActive(IsYesNo.YES);
        this.setInsertDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
    }

    public void update(String answerContent) {
        this.answerContent = answerContent;

        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateStatus(IsYesNo isActive) {
        this.setIsActive(isActive);
        this.setUpdateDate(LocalDateTime.now());
    }
}
