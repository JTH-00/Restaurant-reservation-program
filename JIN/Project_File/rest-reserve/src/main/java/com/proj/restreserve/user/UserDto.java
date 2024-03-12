package com.proj.restreserve.user;

import lombok.Data;

@Data
public class UserDto {
    private String userhash;
    private String useremail;
    private String password;
    private String username;
    private String phone;
    private Role role;
    private Boolean ban;

}
