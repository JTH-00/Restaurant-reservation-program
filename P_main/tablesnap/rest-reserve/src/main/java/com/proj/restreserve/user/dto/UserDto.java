package com.proj.restreserve.user.dto;

import com.proj.restreserve.user.entity.Role;
import lombok.Data;

@Data
public class UserDto {
    private String userid;
    private String useremail;
    private String password;
    private String username;
    private String phone;
    private Role role;
    private Boolean ban;

}