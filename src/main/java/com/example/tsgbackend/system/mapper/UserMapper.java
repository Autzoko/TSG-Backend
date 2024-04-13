package com.example.tsgbackend.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tsgbackend.system.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectUserById(String user_id) throws Exception;
    void insertUser(User user) throws Exception;
    void deleteUserById(String user_id) throws Exception;
    List<User> selectUserByName(String user_name) throws Exception;
}
