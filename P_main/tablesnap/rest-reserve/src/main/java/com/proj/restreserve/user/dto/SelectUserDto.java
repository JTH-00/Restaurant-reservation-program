package com.proj.restreserve.user.dto;

import com.proj.restreserve.user.entity.Role;
import lombok.Data;

@Data
public class SelectUserDto {
    private String userid;
    private String useremail;
    private String username;
    private String phone;
    private Role role;
    private Boolean ban;
}
