package com.project.board.main.api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMemberUpdatePassword {
    private String oldPassword;
    private String newPassword;
}
