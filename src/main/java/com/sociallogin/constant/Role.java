package com.sociallogin.constant;

import com.sociallogin.core.code.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role implements EnumType {
    USER("회원"),
    ADMIN("관리자");

    private final String desc;

    @Override
    public String getCode() {
        return name();
    } // "USER", "ADMIN"
}
