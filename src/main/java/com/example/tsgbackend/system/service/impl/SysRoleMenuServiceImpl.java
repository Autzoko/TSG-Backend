package com.example.tsgbackend.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.tsgbackend.common.constant.CommonConstants;
import com.example.tsgbackend.common.exception.BadRequestException;
import com.example.tsgbackend.system.bean.SysRole;
import com.example.tsgbackend.system.bean.SysRoleMenu;
import com.example.tsgbackend.system.bean.dto.RoleMenuDto;
import com.example.tsgbackend.system.mapper.SysRoleMapper;
import com.example.tsgbackend.system.mapper.SysRoleMenuMapper;
import com.example.tsgbackend.system.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    private final SysRoleMenuMapper roleMenuMapper;
    private final SysRoleMapper roleMapper;

    @Override
    public List<SysRoleMenu> getMenuByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        return roleMenuMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public void editMenuRoleByRoleId(RoleMenuDto roleMenuDto) {
        Long roleId = roleMenuDto.getRoleId();
        // Get Current Role Info
        SysRole role = roleMapper.selectById(roleId);
        if(role == null) {
            throw new BadRequestException("current role not exist");
        }
        if(CommonConstants.ROLE_ADMIN.equals(role.getRoleCode())) {
            throw new BadRequestException("super admin, no need for authentication");
        }
        // Clear All Menus of Previous Role
        deleteByRoleId(roleId);
        // Traverse All Menu
        if(!CollectionUtils.isEmpty(roleMenuDto.getMenuIds())) {
            roleMenuDto.getMenuIds().forEach(menuId -> {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setMenuId(menuId);
                roleMenu.setRoleId(roleId);
                roleMenuMapper.insert(roleMenu);
            });
        }
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        roleMenuMapper.delete(wrapper);
    }
}
