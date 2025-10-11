package com.project.board.main.api.dto.member.admin;

import com.project.board.main.api.dto.constant.member.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegist {
    private String adminID;
    private String adminPassword;
    private String adminName;
    private String adminNickName;
    private String adminPhone;
    private String adminEmail;
    private MemberRole adminRole;
    private String adminDescription;
}
