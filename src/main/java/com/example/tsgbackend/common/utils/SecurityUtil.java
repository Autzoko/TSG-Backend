package com.example.tsgbackend.common.utils;

import com.example.tsgbackend.common.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SecurityUtil {
    /**
     * @Description Get Current User
     * @Param: []
     * @Return: org.springframework.security.core.userdetails.UserDetails
     */
    public static UserDetails getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "log in expired");
        }
        if(authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDetailsService userDetailsService = SpringContextH
        }
    }
}
