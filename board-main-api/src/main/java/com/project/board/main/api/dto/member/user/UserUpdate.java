package com.project.board.main.api.dto.member.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdate {
    private UUID userUUID;
    private String userName;
    private String userNickName;
    private String userPhone;
    private String userEmail;
    private String userDescription;
}
