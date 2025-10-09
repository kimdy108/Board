package com.project.board.main.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMemberInfo {
    private String userId;
    private String userName;
    private String userNickName;
    private String userEmail;
    private String userPhone;

    public static BoardMemberInfo create(String userId,
                                         String userName,
                                         String userNickName,
                                         String userEmail,
                                         String userPhone) {
        BoardMemberInfo boardMemberInfo = new BoardMemberInfo();
        boardMemberInfo.setUserId(userId);
        boardMemberInfo.setUserName(userName);
        boardMemberInfo.setUserNickName(userNickName);
        boardMemberInfo.setUserEmail(userEmail);
        boardMemberInfo.setUserPhone(userPhone);
        return boardMemberInfo;
    }
}
