package com.project.board.main.api.dto.constant.member;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum MemberApprovalType {
    APPROVE("승인"),
    REJECT("차단"),
    WAIT("대기");

    private final String value;

    MemberApprovalType(String value) {
        this.value = value;
    }

    public static MemberApprovalType fromValue(String value) {
        return MemberApprovalType.valueOf(value);
    }
}
