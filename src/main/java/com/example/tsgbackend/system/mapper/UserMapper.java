package com.example.tsgbackend.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tsgbackend.system.bean.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDto> {
    UserDto selectUserById(String user_id) throws Exception;
    void insertUser(UserDto userDto) throws Exception;
    void deleteUserById(String user_id) throws Exception;
    List<UserDto> selectUserByName(String user_name) throws Exception;
}
