package com.example.tsgbackend.system.bean.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDto {

    private Long id;

    private String username;

    private String nickName;

    private String email;

    private String password;

    private List<String> roles;

    private Boolean enabled;

    private List<String> roleIds;

    private String uuid;

    private String code;
}
