package com.example.tsgbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tsgbackend.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectUserById(String user_id);
    void insertUser(User user);
}
