package com.project.board.main.api.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_member", indexes = {
        @Index(name = "idx_seq", columnList = "seq"),
        @Index(name = "idx_member_id", columnList = "member_id"),
        @Index(name = "idx_member_guid", columnList = "member_guid")
})
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMember {
    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name = "member_guid")
    private String memberGuid;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_password")
    private String memberPassword;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_nick_name")
    private String memberNickName;

    @Column(name = "member_phone")
    private String memberPhone;

    @Column(name = "member_email")
    private String memberEmail;

    @Column(name = "member_role")
    private String memberRole;

    @Column(name = "use_flag")
    private boolean useFlag;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    public static BoardMember create(String memberGuid,
                                     String memberId,
                                     String memberPassword,
                                     String memberName,
                                     String memberNickName,
                                     String memberPhone,
                                     String memberEmail,
                                     String memberRole
    ){
        BoardMember member = new BoardMember();
        member.setMemberGuid(memberGuid);
        member.setMemberId(memberId);
        member.setMemberPassword(memberPassword);
        member.setMemberName(memberName);
        member.setMemberNickName(memberNickName);
        member.setMemberPhone(memberPhone);
        member.setMemberEmail(memberEmail);
        member.setMemberRole(memberRole);
        member.setUseFlag(true);
        member.setUpdateDate(LocalDateTime.now());
        member.setInsertDate(LocalDateTime.now());
        return member;
    }

    public void updateInfo(String memberName,
                           String memberEmail,
                           String memberPhone) {
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberPhone = memberPhone;
        this.updateDate = LocalDateTime.now();
    }

    public void updatePassword(String memberPassword) {
        this.memberPassword = memberPassword;
        this.updateDate = LocalDateTime.now();
    }

    public void updateUseFalg() {
        this.useFlag = false;
        this.updateDate = LocalDateTime.now();
    }
}
