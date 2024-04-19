package com.example.tsgbackend.system.controller;

import com.example.tsgbackend.common.utils.ResultUtil;
import com.example.tsgbackend.system.service.SysRoleService;
import com.example.tsgbackend.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authenticate")
public class LoginController extends ResultUtil {

    private final SysUserService userService;

    private final SysRoleService roleService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

}
