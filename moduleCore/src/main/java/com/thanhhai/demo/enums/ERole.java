package com.thanhhai.demo.enums;

public enum ERole {
    ROLE_SUPER,
    ROLE_ADMIN, // 管理者
    ROLE_OPERATOR, // オペレーター
    ROLE_USER;

    public static ERole fromName(String name) {
        for (ERole eRole : ERole.values()) {
            if (name.equals(eRole.name())) {
                return eRole;
            }
        }

        return ERole.ROLE_USER;
    }
}