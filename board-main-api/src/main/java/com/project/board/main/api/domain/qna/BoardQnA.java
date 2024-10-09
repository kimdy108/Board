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

    @Column(name = "qna_title")
    private String qnaTitle;

    @Column(name = "qna_content")
    private String qnaContent;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;
}
