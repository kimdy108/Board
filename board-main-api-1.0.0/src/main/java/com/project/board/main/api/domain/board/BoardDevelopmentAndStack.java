package com.project.board.main.api.domain.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_development_stack", indexes = {
        @Index(name = "idx_seq", columnList = "seq"),
        @Index(name = "idx_development_stack_title", columnList = "development_stack_title"),
        @Index(name = "idx_member_guid", columnList = "member_guid")
})
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDevelopmentAndStack {
    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name = "member_guid")
    private String memberGuid;

    @Column(name = "development_stack_guid")
    private String developmentStackGuid;

    @Column(name = "development_stack_title")
    private String developmentStackTitle;

    @Column(name = "development_stack_content")
    private String developmentStackContent;

    @Column(name = "use_flag")
    private boolean useFlag;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    public static BoardDevelopmentAndStack regist(String memberGuid,
                                                  String developmentStackGuid,
                                                  String developmentStackTitle,
                                                  String developmentStackContent) {
        BoardDevelopmentAndStack boardDevelopmentAndStack = new BoardDevelopmentAndStack();
        boardDevelopmentAndStack.setMemberGuid(memberGuid);
        boardDevelopmentAndStack.setDevelopmentStackGuid(developmentStackGuid);
        boardDevelopmentAndStack.setDevelopmentStackTitle(developmentStackTitle);
        boardDevelopmentAndStack.setDevelopmentStackContent(developmentStackContent);
        boardDevelopmentAndStack.setUseFlag(true);
        boardDevelopmentAndStack.setUpdateDate(LocalDateTime.now());
        boardDevelopmentAndStack.setInsertDate(LocalDateTime.now());
        return boardDevelopmentAndStack;
    }

    public void update(String developmentStackTitle,
                       String developmentStackContent) {
        this.developmentStackTitle = developmentStackTitle;
        this.developmentStackContent = developmentStackContent;
        this.updateDate = LocalDateTime.now();
    }

    public void updateUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
        this.updateDate = LocalDateTime.now();
    }
}
