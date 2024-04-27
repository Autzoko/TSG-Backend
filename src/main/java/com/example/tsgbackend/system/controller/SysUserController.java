package com.example.tsgbackend.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.tsgbackend.common.exception.BadRequestException;
import com.example.tsgbackend.common.utils.ResultUtil;
import com.example.tsgbackend.common.utils.StringUtil;
import com.example.tsgbackend.logs.annotation.Log;
import com.example.tsgbackend.system.bean.SysUser;
import com.example.tsgbackend.system.bean.dto.QueryDto;
import com.example.tsgbackend.system.bean.dto.UserDto;
import com.example.tsgbackend.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class SysUserController extends ResultUtil {

    private final SysUserService userService;

    @Log("Query User List")
    @GetMapping("/user/table")
    public ResponseEntity<Object> queryUserTable(QueryDto queryDto) {
        try {
            return success(true, userService.queryUserTable(queryDto));
        } catch(BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    @Log("Edit User")
    @PostMapping("/user/edit")
    public ResponseEntity<Object> editUser(@RequestBody UserDto userDto) {
        try {
            String str = StringUtil.getEditType(userDto.getId());
            userService.editUser(userDto);
            return success(true, str);
        } catch(BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    @Log("Delete User")
    @DeleteMapping("/user/del")
    public ResponseEntity<Object> delUser(String id) {
        try {
            userService.delUser(id);
            return success(true, "delete success");
        } catch (BadRequestException e) {
            return fail(false, "delete failed");
        }
    }

    @Log("Modify User Status")
    @PutMapping("/user/enabled")
    public ResponseEntity<Object> enableUser(@RequestBody SysUser sysUser) {
        String str = sysUser.isEnabled() ? "Enable" : "Disable";
        try {
            userService.enabledUser(sysUser);
            return success(true, str + " success");
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    @Log(value = "Change User Password")
    @PutMapping("/user/password")
    public ResponseEntity<Object> updatePassword(@RequestBody JSONObject jsonObject) {
        try {
            userService.updatePassword(jsonObject);
            return success(true, "update success");
        } catch(BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }
}
