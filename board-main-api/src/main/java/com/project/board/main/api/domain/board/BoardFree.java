package com.project.board.main.api.domain.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_free", indexes = {
        @Index(name = "idx_seq", columnList = "seq"),
        @Index(name = "idx_free_title", columnList = "free_title"),
        @Index(name = "idx_member_guid", columnList = "member_guid")
})
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardFree {
    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name = "member_guid")
    private String memberGuid;

    @Column(name = "free_title")
    private String freeTitle;

    @Column(name = "free_content")
    private String freeContent;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;
}
