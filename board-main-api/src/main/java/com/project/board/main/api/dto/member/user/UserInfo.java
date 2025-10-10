package com.project.board.main.api.dto.member.user;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.project.board.main.api.utils.Common.decryptString;
import static com.project.board.main.api.utils.Common.encryptStringSalt;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String userID;
    private String userName;
    private String userNickName;
    private String userPhone;
    private String userEmail;
    private MemberApprovalType userApproval;
    private String userDescription;

    public static UserInfo create(BoardMainMember boardMainMember) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserID(encryptStringSalt(boardMainMember.getMemberID()));
        userInfo.setUserName(encryptStringSalt(decryptString(boardMainMember.getMemberName())));
        userInfo.setUserNickName(encryptStringSalt(boardMainMember.getMemberNickName()));
        userInfo.setUserPhone(encryptStringSalt(decryptString(boardMainMember.getMemberPhone())));
        userInfo.setUserEmail(encryptStringSalt(decryptString(boardMainMember.getMemberEmail())));
        userInfo.setUserApproval(boardMainMember.getMemberApproval());
        userInfo.setUserDescription(boardMainMember.getDescriptionNote());
        return userInfo;
    }
}
