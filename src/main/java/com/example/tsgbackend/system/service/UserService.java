package com.example.tsgbackend.system.service;

import com.example.tsgbackend.system.bean.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto selectUserById(String user_id) throws Exception;
    void insertUser(UserDto userDto) throws Exception;
    void deleteUserById(String user_id) throws Exception;
    List<UserDto> selectUserByName(String user_name) throws Exception;
}
