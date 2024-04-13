package com.example.tsgbackend.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.tsgbackend.common.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

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
            UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
            return userDetailsService.loadUserByUsername(userDetails.getUsername());
        }
        throw new BadRequestException(HttpStatus.UNAUTHORIZED, "cannot find current login info");
    }

    /**
     * @Description Get System Username
     * @Param []
     * @Return java.lang.String
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "log in expired");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    /**
     * @Description Get System User Id
     * @Param []
     * @Return java.lang.String
     */
    public static String getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();
        if(userDetails != null) {
            return JSON.parseObject(JSONObject.toJSONString(userDetails)).getJSONObject("user").getString("id");
        } else {
            return null;
        }
    }

    /**
     * @Description Query System User List
     * @Param []
     * @Return java.util.List<java.lang.String>
     */
    public static List<String> getCurrentRoles() {
        UserDetails userDetails = getCurrentUser();
        List<String> list = new ArrayList<>();
        if(userDetails != null) {
            userDetails.getAuthorities().forEach((item) -> {
                list.add(item.toString());
            });
        }
        return list;
    }
}
