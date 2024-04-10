package com.example.tsgbackend.service;

import com.example.tsgbackend.bean.User;

public interface UserService {
    User selectUserById(String user_id) throws Exception;
    void insertUser(User user) throws Exception;
    void deleteUserById(String user_id) throws Exception;
    User selectUserByName(String user_name) throws Exception;
}
