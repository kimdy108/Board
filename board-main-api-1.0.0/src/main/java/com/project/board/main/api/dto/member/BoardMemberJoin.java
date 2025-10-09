package com.project.board.main.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMemberJoin {
    private String id;
    private String name;
    private String password;
    private String nickName;
    private String phone;
    private String email;
}
