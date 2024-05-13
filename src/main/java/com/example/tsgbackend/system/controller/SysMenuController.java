package com.example.tsgbackend.system.controller;

import com.example.tsgbackend.common.exception.BadRequestException;
import com.example.tsgbackend.common.utils.ResultUtil;
import com.example.tsgbackend.common.utils.SecurityUtil;
import com.example.tsgbackend.common.utils.StringUtil;
import com.example.tsgbackend.logs.annotation.Log;
import com.example.tsgbackend.system.bean.SysMenu;
import com.example.tsgbackend.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class SysMenuController extends ResultUtil {

    private final SysMenuService menuService;

    /**
     * @description get menu tree according to role
     * @return org.springframework.http.ResponseEntity
     */
    @Log("Get Menu Tree")
    @GetMapping("/menu/tree")
    public ResponseEntity<Object> getMenuTree() {
        try {
            List<String> roles = SecurityUtil.getCurrentRoles();
            return success(true, menuService.getMenuTree(roles));
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    /**
     * @description get permission list
     * @return org.springframework.http.ResponseEntity
     */
    @Log(value = "get permission list")
    @GetMapping("/menu/permission")
    public ResponseEntity<Object> getPermission() {
        try {
            return success(true, menuService.getPermission());
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    /**
     * @description query all permission menu of current user
     * @return org.springframework.http.ResponseEntity
     */
    @GetMapping("/menu/all")
    public ResponseEntity<Object> queryAllMenus() {
        try {
            List<String> roles = SecurityUtil.getCurrentRoles();
            return success(true, menuService.queryAllMenus(roles));
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    /**
     * @description query menu table
     * @param blurry query command
     * @return org.springframework.http.ResponseEntity
     */
    @Log("Get Menu Table")
    @GetMapping("/menu/table")
    public ResponseEntity<Object> getMenuTable(String blurry) {
        try {
            return success(true, menuService.getMenuTable(blurry));
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    /**
     * @description edit current menu
     * @param sysMenu new menu info
     * @return org.springframework.http.ResponseEntity
     */
    @Log("Edit Menu")
    @PostMapping("/menu/edit")
    public ResponseEntity<Object> editMenu(@RequestBody SysMenu sysMenu) {
        try {
            String str = StringUtil.getEditType(sysMenu.getId());
            menuService.editMenu(sysMenu);
            return success(true, str);
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    /**
     * @description delete menu by id
     * @param id menu id
     * @return org.springframework.http.ResponseEntity
     */
    @Log("Delete Menu")
    @DeleteMapping("/menu/del")
    public ResponseEntity<Object> delMenu(Long id) {
        try {
            menuService.delMenu(id);
            return success(true, "delete success");
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    /**
     * @description get select item of menu
     * @return org.springframework.http.ResponseEntity
     */
    @Log(value = "get draw tree menu")
    @GetMapping("/menu/select")
    public ResponseEntity<Object> getMenuTreeSelect() {
        try {
            return success(true, menuService.getMenuTreeSelect());
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }
}
