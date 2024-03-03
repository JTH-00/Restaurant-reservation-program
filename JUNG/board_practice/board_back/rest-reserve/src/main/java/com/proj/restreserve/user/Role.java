package com.proj.restreserve.user;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    ROLE_SUPERADMIN("SUPERADMIN");

    private final String key;

    public String getKey() {
        return key;
    }
}