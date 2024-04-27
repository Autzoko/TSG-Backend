package com.example.tsgbackend.system.controller;

import com.example.tsgbackend.common.exception.BadRequestException;
import com.example.tsgbackend.common.utils.ResultUtil;
import com.example.tsgbackend.logs.annotation.Log;
import com.example.tsgbackend.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class SysRoleController extends ResultUtil {

    private final SysRoleService roleService;

    @Log("Get Role List")
    @GetMapping("/role/table")
    public ResponseEntity<Object> getRoleList(String blurry) {
        try {
            return success(true, roleService.getRoleList(blurry));
        } catch(BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }
}
