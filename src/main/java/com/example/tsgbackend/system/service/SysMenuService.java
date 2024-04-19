package com.example.tsgbackend.system.service;

import com.alibaba.fastjson.JSONArray;
import com.example.tsgbackend.system.bean.SysMenu;

import java.util.List;

public interface SysMenuService {

    /**
     * @description Get Menu Tree
     * @param roles
     * @return com.alibaba.fastjson.JSONArray
     */
    JSONArray getMenuTree(List<String> roles);

    /**
     * @description edit menu
     * @param sysMenu
     * @return void
     */
    void editMenu(SysMenu sysMenu);

    /**
     * @description delete menu
     * @param id
     * @return void
     */
    void delMenu(Long id);

    /**
     * @description query current role's menu of permissions
     * @param roles
     * @return java.util.List<com.example.tsgbackend.system.bean.SysMenu>
     */
    List<SysMenu> queryAllMenus(List<String> roles);

    /**
     * @description get menu list
     * @param blurry
     * @return com.alibaba.fastjson.JSONArray
     */
    JSONArray getMenuTable(String blurry);

    /**
     * @description get all authorized menu by role
     * @param currentRoles
     * @return java.util.List<com.example.tsgbackend.system.bean.SysMenu>
     */
    List<String> getUrlsByRoles(List<String> currentRoles);

    /**
     * @description get permissions list
     * @return java.util.List<java.lang.String>
     */
    List<String> getPermission();

    /**
     * @description get menu down pull tree
     * @return com.alibaba.fastjson.JSONArray
     */
    JSONArray getMenuTreeSelect();
}
