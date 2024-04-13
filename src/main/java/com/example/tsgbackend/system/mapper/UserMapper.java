package com.example.tsgbackend.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tsgbackend.system.bean.dto.deprecatedUserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<deprecatedUserDto> {
    deprecatedUserDto selectUserById(String user_id) throws Exception;
    void insertUser(deprecatedUserDto deprecatedUserDto) throws Exception;
    void deleteUserById(String user_id) throws Exception;
    List<deprecatedUserDto> selectUserByName(String user_name) throws Exception;
}
