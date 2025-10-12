package com.project.board.main.api.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdate {
    private UUID memberUUID;
    private String memberName;
    private String memberNickName;
    private String memberPhone;
    private String memberEmail;
    private String memberDescription;
}
