package com.example.tsgbackend.system.service;

import com.example.tsgbackend.system.bean.SysUser;

public interface SysUserService {
    /**
     * @Description: Find User By Namw
     * @Param: [userName]
     */
    SysUser findByName(String userName);
}
