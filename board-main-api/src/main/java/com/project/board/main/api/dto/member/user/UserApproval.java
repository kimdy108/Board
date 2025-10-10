package com.project.board.main.api.dto.member.user;

import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserApproval {
    private UUID userUUID;
    private MemberApprovalType userApproval;
}
