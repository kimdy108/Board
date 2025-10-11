package com.project.board.main.api.dto.member.admin;

import com.project.board.main.api.dto.constant.member.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminList {
    private UUID adminUUID;
    private String adminID;
    private String adminName;
    private String adminNickName;
    private MemberRole adminRole;
    private LocalDateTime insertDate;
}
