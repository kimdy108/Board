package com.project.board.main.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthTokenBase {
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberNickName;
    private String memberRole;

    public static AuthTokenBase create(String memberId,
                                       String memberPassword,
                                       String memberName,
                                       String memberNickName,
                                       String memberRole) {
        AuthTokenBase authTokenBase = new AuthTokenBase();
        authTokenBase.setMemberId(memberId);
        authTokenBase.setMemberPassword(memberPassword);
        authTokenBase.setMemberName(memberName);
        authTokenBase.setMemberNickName(memberNickName);
        authTokenBase.setMemberRole(memberRole);
        return authTokenBase;
    }
}
