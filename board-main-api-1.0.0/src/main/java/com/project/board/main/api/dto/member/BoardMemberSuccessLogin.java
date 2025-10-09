package com.project.board.main.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMemberSuccessLogin {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String userGuid;
    private String userNickName;
    private String userRole;
    private boolean outPut;

    public static BoardMemberSuccessLogin create(String accessToken,
                                                 String refreshToken,
                                                 String userId,
                                                 String userGuid,
                                                 String userNickName,
                                                 String userRole) {
        BoardMemberSuccessLogin boardMemberSuccessLogin = new BoardMemberSuccessLogin();
        boardMemberSuccessLogin.setAccessToken(accessToken);
        boardMemberSuccessLogin.setRefreshToken(refreshToken);
        boardMemberSuccessLogin.setUserId(userId);
        boardMemberSuccessLogin.setUserGuid(userGuid);
        boardMemberSuccessLogin.setUserNickName(userNickName);
        boardMemberSuccessLogin.setUserRole(userRole);
        boardMemberSuccessLogin.setOutPut(true);
        return boardMemberSuccessLogin;
    }
}
