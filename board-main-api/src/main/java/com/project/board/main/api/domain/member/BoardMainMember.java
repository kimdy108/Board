package com.project.board.main.api.domain.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.board.main.api.domain.common.BoardCommonBase;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "board_main_member", indexes = {
        @Index(name = "idx_member_uuid", columnList = "member_uuid"),
        @Index(name = "idx_member_id", columnList = "member_id"),
        @Index(name = "idx_member_name", columnList = "member_name")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMainMember extends BoardCommonBase {
    @Comment("사용자 UUID")
    @Column(name = "member_uuid", length = 50, unique = true, nullable = false, updatable = false)
    private UUID memberUUID;

    @Comment("사용자 ID")
    @Column(name = "member_id", length = 20, unique = true, nullable = false, updatable = false)
    private String memberID;

    @Comment("사용자 비밀번호")
    @Column(name = "member_password", length = 200, nullable = false)
    private String memberPassword;

    @Comment("사용자 이름")
    @Column(name = "member_name", length = 200, nullable = false)
    private String memberName;

    @Comment("사용자 별명")
    @Column(name = "member_nick_name", length = 200, nullable = false)
    private String memberNickName;

    @Comment("사용자 전화번호")
    @Column(name = "member_phone", length = 200, nullable = false)
    private String memberPhone;

    @Comment("사용자 Email")
    @Column(name = "member_email", length = 200, nullable = false)
    private String memberEmail;

    @Comment("사용자 승인 상태")
    @Column(name = "member_approval", columnDefinition = "ENUM('APPROVE','REJECT','WAIT') NOT NULL DEFAULT 'WAIT'")
    @Enumerated(EnumType.STRING)
    private MemberApprovalType memberApproval;

    @Comment("마지막 접근날짜")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "last_date", columnDefinition = "DATETIME")
    private LocalDateTime lastDate;

    @Builder
    public BoardMainMember(UUID memberUUID, String memberID, String memberPassword, String memberName, String memberNickName, String memberPhone, String memberEmail, MemberApprovalType memberApproval, LocalDateTime lastDate, IsYesNo isActive, LocalDateTime insertDate, LocalDateTime updateDate, String descriptionNote) {
        this.memberUUID = memberUUID;
        this.memberID = memberID;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberNickName = memberNickName;
        this.memberPhone = memberPhone;
        this.memberEmail = memberEmail;
        this.memberApproval = memberApproval;
        this.lastDate = lastDate;

        this.setIsActive(isActive);
        this.setInsertDate(insertDate);
        this.setUpdateDate(updateDate);
        this.setDescriptionNote(descriptionNote);
    }
}
