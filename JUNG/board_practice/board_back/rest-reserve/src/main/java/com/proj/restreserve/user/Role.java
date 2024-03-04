package com.proj.restreserve.user;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER("USER"), //사용자
    ROLE_ADMIN("ADMIN"), //업주
    ROLE_SUPERADMIN("SUPERADMIN"); //관리자

    private final String key;

    public String getKey() {
        return key;
    }
}