package com.project.board.main.api.dto.member.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUp {
    private String userID;
    private String userPassword;
    private String userName;
    private String userNickName;
    private String userPhone;
    private String userEmail;
}
