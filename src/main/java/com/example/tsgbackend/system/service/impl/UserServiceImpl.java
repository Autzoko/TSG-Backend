package com.example.tsgbackend.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tsgbackend.system.bean.User;
import com.example.tsgbackend.system.mapper.UserMapper;
import com.example.tsgbackend.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    public List<User> selectUserByName(String user_name) throws Exception {
        return userMapper.selectUserByName(user_name);
    }

    public void insertUser(User user) throws Exception {
        userMapper.insertUser(user);
    }

    public void deleteUserById(String user_id) throws Exception {
        userMapper.deleteUserById(user_id);
    }

    public User selectUserById(String user_id) throws Exception {
        return userMapper.selectUserById(user_id);
    }
}
