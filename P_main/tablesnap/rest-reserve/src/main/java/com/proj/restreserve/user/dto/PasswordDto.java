package com.proj.restreserve.user.dto;

import lombok.Data;

@Data
public class PasswordDto {
    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirm;
}
