package com.project.board.main.api.domain.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.board.main.api.domain.common.BoardCommonBase;
import com.project.board.main.api.dto.constant.admin.AdminRole;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "board_main_admin", indexes = {
        @Index(name = "idx_admin_id", columnList = "admin_id"),
        @Index(name = "idx_admin_uuid", columnList = "admin_uuid"),
        @Index(name = "idx_admin_name", columnList = "admin_name")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMainAdmin extends BoardCommonBase {
    @Comment("관리자 UUID")
    @Column(name = "admin_uuid", length = 50, unique = true, nullable = false, updatable = false)
    private UUID adminUUID;

    @Comment("관리자 ID")
    @Column(name = "admin_id", length = 20, unique = true, nullable = false, updatable = false)
    private String adminID;

    @Comment("관리자 비밀번호")
    @Column(name = "admin_password", length = 200, nullable = false)
    private String adminPassword;

    @Comment("관리자 이름")
    @Column(name = "admin_name", length = 200, nullable = false)
    private String adminName;

    @Comment("관리자 전화번호")
    @Column(name = "admin_phone", length = 200, nullable = false)
    private String adminPhone;

    @Comment("관리자 Email")
    @Column(name = "admin_email", length = 200, nullable = false)
    private String adminEmail;

    @Comment("관리자 권한")
    @Column(name = "admin_role", columnDefinition = "ENUM('MASTER','ADMIN','MANAGER') NOT NULL DEFAULT 'MANAGER'")
    @Enumerated(EnumType.STRING)
    private AdminRole adminRole;

    @Comment("관리자 형식")
    @Column(name = "admin_type", columnDefinition = "ENUM('SYSTEM','NORMAL') NOT NULL DEFAULT 'SYSTEM'")
    private String adminType;

    @Comment("마지막 접근날짜")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "last_date", columnDefinition = "DATETIME")
    private LocalDateTime lastDate;

    @Builder
    public BoardMainAdmin(UUID adminUUID, String adminID, String adminPassword, String adminName, String adminPhone, String adminEmail, AdminRole adminRole, String adminType, LocalDateTime lastDate, IsYesNo isActive, LocalDateTime insertDate, LocalDateTime updateDate, String descriptionNote) {
        this.adminUUID = adminUUID;
        this.adminID = adminID;
        this.adminPassword = adminPassword;
        this.adminName = adminName;
        this.adminPhone = adminPhone;
        this.adminEmail = adminEmail;
        this.adminRole = adminRole;
        this.adminType = adminType;
        this.lastDate = lastDate;

        this.setIsActive(isActive);
        this.setInsertDate(insertDate);
        this.setUpdateDate(updateDate);
        this.setDescriptionNote(descriptionNote);
    }
}
