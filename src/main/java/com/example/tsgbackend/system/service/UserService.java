package com.example.tsgbackend.system.service;

import com.example.tsgbackend.system.bean.dto.deprecatedUserDto;

import java.util.List;

public interface UserService {
    deprecatedUserDto selectUserById(String user_id) throws Exception;
    void insertUser(deprecatedUserDto deprecatedUserDto) throws Exception;
    void deleteUserById(String user_id) throws Exception;
    List<deprecatedUserDto> selectUserByName(String user_name) throws Exception;
}
