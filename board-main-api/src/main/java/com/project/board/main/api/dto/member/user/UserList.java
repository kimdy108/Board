package com.project.board.main.api.dto.member.user;

import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserList {
    private UUID userUUID;
    private String userID;
    private String userName;
    private String userNickName;
    private MemberApprovalType userApproval;
    private LocalDateTime insertDate;
}
