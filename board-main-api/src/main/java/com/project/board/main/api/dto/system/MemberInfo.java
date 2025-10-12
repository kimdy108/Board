package com.project.board.main.api.dto.system;

import com.project.board.main.api.domain.member.BoardMainMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.project.board.main.api.utils.Common.decryptString;
import static com.project.board.main.api.utils.Common.encryptStringSalt;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfo {
    private String memberID;
    private String memberName;
    private String memberNickName;
    private String memberPhone;
    private String memberEmail;
    private String memberDescription;

    public static MemberInfo create(BoardMainMember boardMainMember) {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberID(encryptStringSalt(boardMainMember.getMemberID()));
        memberInfo.setMemberName(encryptStringSalt(decryptString(boardMainMember.getMemberName())));
        memberInfo.setMemberNickName(encryptStringSalt(boardMainMember.getMemberNickName()));
        memberInfo.setMemberPhone(encryptStringSalt(decryptString(boardMainMember.getMemberPhone())));
        memberInfo.setMemberEmail(encryptStringSalt(decryptString(boardMainMember.getMemberEmail())));
        memberInfo.setMemberDescription(boardMainMember.getDescriptionNote());
        return memberInfo;
    }
}
