package com.project.board.main.api.dto.member.admin;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.constant.member.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.project.board.main.api.utils.Common.decryptString;
import static com.project.board.main.api.utils.Common.encryptStringSalt;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfo {
    private String adminID;
    private String adminName;
    private String adminNickName;
    private String adminPhone;
    private String adminEmail;
    private MemberRole adminRole;
    private String adminDescription;

    public static AdminInfo create(BoardMainMember boardMainMember) {
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setAdminID(encryptStringSalt(boardMainMember.getMemberID()));
        adminInfo.setAdminName(encryptStringSalt(decryptString(boardMainMember.getMemberName())));
        adminInfo.setAdminNickName(encryptStringSalt(boardMainMember.getMemberNickName()));
        adminInfo.setAdminPhone(encryptStringSalt(decryptString(boardMainMember.getMemberPhone())));
        adminInfo.setAdminEmail(encryptStringSalt(decryptString(boardMainMember.getMemberEmail())));
        adminInfo.setAdminRole(boardMainMember.getMemberRole());
        adminInfo.setAdminDescription(boardMainMember.getDescriptionNote());
        return adminInfo;
    }
}
