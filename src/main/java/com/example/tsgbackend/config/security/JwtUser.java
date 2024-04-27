package com.example.tsgbackend.config.security;

import com.example.tsgbackend.system.bean.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtUser {
    private UserDto userDto;
    private String token;
    private String refreshToken;

    /**
     * @Description: Define return object after log in
     * @Param: [token, deprecatedUserDto]
     */
    public JwtUser(String token, String refreshToken, UserDto userDto) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.userDto = userDto;
    }
}
