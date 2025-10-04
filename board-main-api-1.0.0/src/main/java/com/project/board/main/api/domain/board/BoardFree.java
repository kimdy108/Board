package com.project.board.main.api.domain.post;

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

    @Column(name = "free_guid")
    private String freeGuid;

    @Column(name = "free_title")
    private String freeTitle;

    @Column(name = "free_content")
    private String freeContent;

    @Column(name = "use_flag")
    private boolean useFlag;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    public static BoardFree regist(String memberGuid,
                                   String freeGuid,
                                   String freeTitle,
                                   String freeContent) {
        BoardFree boardFree = new BoardFree();
        boardFree.setMemberGuid(memberGuid);
        boardFree.setFreeGuid(freeGuid);
        boardFree.setFreeTitle(freeTitle);
        boardFree.setFreeContent(freeContent);
        boardFree.setUseFlag(true);
        boardFree.setUpdateDate(LocalDateTime.now());
        boardFree.setInsertDate(LocalDateTime.now());
        return boardFree;
    }

    public void update(String freeTitle,
                       String freeContent) {
        this.freeTitle = freeTitle;
        this.freeContent = freeContent;
        this.updateDate = LocalDateTime.now();
    }

    public void updateUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
        this.updateDate = LocalDateTime.now();
    }
}
