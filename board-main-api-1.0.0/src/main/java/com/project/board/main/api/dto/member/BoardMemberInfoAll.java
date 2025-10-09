package com.project.board.main.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMemberInfoAll extends BoardMemberInfo {
    private String userGuid;
    private String userRole;
    private LocalDateTime insertDate;
}
