package com.example.tsgbackend.system.service;

import com.example.tsgbackend.system.bean.SysRoleMenu;
import com.example.tsgbackend.system.bean.dto.RoleMenuDto;

import java.util.List;

public interface SysRoleMenuService {
    /**
     * @Description Get Menu by Role Id
     * @Param [roleId]
     * @Return java.util.List<com.example.tsgbackend.system.bean.SysRoleMenu>
     */
    List<SysRoleMenu> getMenuByRoleId(Long roleId);

    /**
     * @Description Authorize Role Menu
     * @Param [roleMenuDto]
     * @Return void
     */
    void editMenuRoleByRoleId(RoleMenuDto roleMenuDto);

    /**
     * @Description Delete Menu Banded with Role
     * @Param [roleId]
     * @void
     */
    void deleteByRoleId(Long roleId);
}
