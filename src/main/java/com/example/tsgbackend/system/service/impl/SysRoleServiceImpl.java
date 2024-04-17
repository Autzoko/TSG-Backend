package com.example.tsgbackend.system.service.impl;

import com.example.tsgbackend.system.bean.SysRole;
import com.example.tsgbackend.system.mapper.SysRoleMapper;
import com.example.tsgbackend.system.service.SysRoleMenuService;
import com.example.tsgbackend.system.service.SysRoleService;
import com.example.tsgbackend.system.service.SysRoleUserService;
import com.example.tsgbackend.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper roleMapper;

    private final SysRoleUserService roleUserService;

    private final SysRoleMenuService roleMenuService;

    @Override
    public List<SysRole> getRoleList(String blurry) {

    }
}
