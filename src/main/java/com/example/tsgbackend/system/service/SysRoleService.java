package com.example.tsgbackend.system.service;

import com.alibaba.fastjson.JSONArray;
import com.example.tsgbackend.system.bean.SysRole;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface SysRoleService {
    /**
     * @Description Get Role List
     * @Param [blurry]
     * @Return java.util.List<com.example.tsgbackend.system.bean.SysRole>
     */
    List<SysRole> getRoleList(String blurry);

    /**
     * @Description Edit Role
     * @Param [role]
     * @Return void
     */
    void editRole(SysRole role);

    /**
     * @Description Delete Role
     * @Param [id]
     * @Return void
     */
    void delRole(Long id);

    /**
     * @Description Role Select Form
     * @Param [userId]
     * @Return com.alibaba.fastjson.JSONArray
     */
    JSONArray getAllRoleForXm(Long userId);

    /**
     * @Description Get Role by User Id
     * @Param [userId]
     * @Return java.util.List<com.example.tsgbackend.system.bean.SysRole>
     */
    List<SysRole> getRoleByUserId(Long userId);

    /**
     * @Description Get Current User Role List
     * @Param [id]
     * @Return java.util.List<org.springframework.security.core.GrantedAuthority>
     */
    List<GrantedAuthority> getRolesByUser(Long id);
}
