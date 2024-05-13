package com.example.tsgbackend.system.controller;

import com.example.tsgbackend.common.exception.BadRequestException;
import com.example.tsgbackend.common.utils.ResultUtil;
import com.example.tsgbackend.logs.annotation.Log;
import com.example.tsgbackend.system.bean.dto.RoleMenuDto;
import com.example.tsgbackend.system.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sys")
public class SysRoleMenuController extends ResultUtil {

    private final SysRoleMenuService roleMenuService;

    /**
     * @description get role menu
     * @param roleId role if
     * @return org.springframework.http.ResponseEntity
     */
    @Log("Get Role Menu")
    @GetMapping("/role/menu/list")
    public ResponseEntity<Object> getMenuByRoleId(Long roleId) {
        try {
            return success(true, roleMenuService.getMenuByRoleId(roleId));
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }

    /**
     * @description edit mapping relation between role and menu
     * @param roleMenuDto role menu dto
     * @return org.springframework.http.ResponseEntity
     */
    @Log("Authenticate Role Menu")
    @PostMapping("/role/menu/edit")
    public ResponseEntity<Object> editMenuRoleByRoleId(@RequestBody RoleMenuDto roleMenuDto) {
        try {
            roleMenuService.editMenuRoleByRoleId(roleMenuDto);
            return success(true, "authenticate success");
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
    }
}
