package com.example.tsgbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tsgbackend.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectUserById(String user_id) throws Exception;
    void insertUser(User user) throws Exception;
    void deleteUserById(String user_id) throws Exception;
    User selectUserByName(String user_name) throws Exception;
}