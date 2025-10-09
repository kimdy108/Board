package com.project.board.main.api.dto.constant.member;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum MemberRole {
    MASTER("최고 관리자", "ROLE_MASTER"),
    ADMIN("관리자", "ROLE_ADMIN"),
    MANAGER("매니저", "ROLE_MANAGER"),
    MEMBER("사용자", "ROLE_MEMBER");

    private final String title;
    private final String value;

    MemberRole(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public static boolean isOverAdmin(MemberRole memberRole) {
        return memberRole.equals(MemberRole.MASTER) || memberRole.equals(MemberRole.ADMIN);
    }

    public static boolean isOverManager(MemberRole memberRole) {
        return memberRole.equals(MemberRole.MASTER) || memberRole.equals(MemberRole.ADMIN) || memberRole.equals(MemberRole.MANAGER);
    }

    public static MemberRole getValue(String value) {
        return MemberRole.valueOf(value);
    }
}
