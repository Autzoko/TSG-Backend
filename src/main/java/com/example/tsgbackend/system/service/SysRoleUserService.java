package com.example.tsgbackend.system.service;

import com.example.tsgbackend.system.bean.SysRoleUser;

import java.util.List;

public interface SysRoleUserService {
    /**
     * @Description: Query User via Role ID
     * @Param: [roleId]
     * @Return: java.util.List<com.example.tsgbackend.system.SysRoleUser>
     */
    List<SysRoleUser> getRoleUserByRoleId(Long roleId);

    /**
     * @Description: Change User Role
     * @Param: [userId, roleIds]
     * @Return: void
     */
    void edit(Long userId, List<String> roleIds);

    /**
     * @Description: Query Current User's Role
     * @Param: [userId]
     * @Return: java.util.List<com.example.tsgbackend.system.SysRoleUser>
     */
    List<SysRoleUser> getRoleUserByUserId(Long userId);

    /**
     * @Description: Delete Role-User Bind
     * @Param: [userId]
     * @Return: void
     */
    void deleteByUserId(String userId);
}
