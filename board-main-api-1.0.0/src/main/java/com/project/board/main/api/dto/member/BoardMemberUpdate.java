package com.project.board.main.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMemberUpdate {
    private String encryptUserName;
    private String encryptUserEmail;
    private String encryptUserPhone;
}
