package com.example.tsgbackend.system.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.tsgbackend.system.bean.SysUser;
import com.example.tsgbackend.system.bean.dto.QueryDto;
import com.example.tsgbackend.system.bean.dto.UserDto;

public interface SysUserService {
    /**
     * @Description: Find User By Namw
     * @Param: [userName]
     * @Return com.example.tsgbackend.system.bean.SysUser
     */
    SysUser findByName(String userName);

    /**
     * @Description Edit User
     * @Param [userDto]
     * @Return void
     */
    void editUser(UserDto userDto);

    /**
     * @Description Query User List
     * @Param [queryDto]
     * @Return java.util.List<com.example.tsgbackend.system.bean.SysUser>
     */
    IPage<UserDto> queryUserTable(QueryDto queryDto);

    /**
     * @Description Query User By Username
     * @Param [userName]
     * @Return com.example.tsgbackend.system.bean.dto.UserDto
     */
    UserDto loadByName(String userName);

    /**
     * @Description Delete User
     * @Param [id]
     * @Return void
     */
    void delUser(String id);

    /**
     * @Description Modify User State
     * @Param [sysUser]
     * @Return void
     */
    void enabledUser(SysUser sysUser);

    /**
     * @Description Change User Password
     * @Param [jsonObject]
     * @Return void
     */
    void updatePassword(JSONObject jsonObject);
}
