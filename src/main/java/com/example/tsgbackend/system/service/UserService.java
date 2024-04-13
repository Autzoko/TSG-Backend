package com.example.tsgbackend.system.service;

import com.example.tsgbackend.system.bean.User;

import java.util.List;

public interface UserService {
    User selectUserById(String user_id) throws Exception;
    void insertUser(User user) throws Exception;
    void deleteUserById(String user_id) throws Exception;
    List<User> selectUserByName(String user_name) throws Exception;
}
