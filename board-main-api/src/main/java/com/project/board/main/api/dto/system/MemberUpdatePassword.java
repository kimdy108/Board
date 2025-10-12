package com.project.board.main.api.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdatePassword {
    private UUID memberUUID;
    private String oldPassword;
    private String newPassword;
}
