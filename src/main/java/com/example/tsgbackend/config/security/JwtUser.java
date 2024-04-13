package com.example.tsgbackend.config.security;

import com.example.tsgbackend.system.bean.dto.deprecatedUserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtUser {
    private deprecatedUserDto deprecatedUserDto;
    private String token;
    private String refreshToken;

    /**
     * @Description: Define return object after log in
     * @Param: [token, deprecatedUserDto]
     */
    public JwtUser(String token, String refreshToken, deprecatedUserDto deprecatedUserDto) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.deprecatedUserDto = deprecatedUserDto;
    }
}
