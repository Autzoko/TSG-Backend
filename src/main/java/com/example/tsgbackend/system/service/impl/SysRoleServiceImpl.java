package com.example.tsgbackend.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.tsgbackend.common.constant.CommonConstants;
import com.example.tsgbackend.common.exception.BadRequestException;
import com.example.tsgbackend.system.bean.SysRole;
import com.example.tsgbackend.system.bean.SysRoleUser;
import com.example.tsgbackend.system.mapper.SysRoleMapper;
import com.example.tsgbackend.system.service.SysRoleMenuService;
import com.example.tsgbackend.system.service.SysRoleService;
import com.example.tsgbackend.system.service.SysRoleUserService;
import com.example.tsgbackend.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper roleMapper;

    private final SysRoleUserService roleUserService;

    private final SysRoleMenuService roleMenuService;

    @Override
    public List<SysRole> getRoleList(String blurry) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(blurry)) {
            wrapper.like(SysRole::getRoleName, blurry);
            wrapper.or();
            wrapper.like(SysRole::getRoleCode, blurry);
            wrapper.or();
            wrapper.like(SysRole::getDescription, blurry);
        }
        wrapper.ne(SysRole::getRoleCode, CommonConstants.ROLE_ADMIN);
        return roleMapper.selectList(wrapper);
    }

    @Override
    public void editRole(SysRole role) {
        checkRole(role);
        if(role.getId() != null) {
            roleMapper.updateById(role);
        } else {
            roleMapper.insert(role);
        }
    }

    @Override
    public void delRole(Long id) {
        checkRoleUser(id);
        roleMenuService.deleteByRoleId(id);
        roleMapper.deleteById(id);
    }

    @Override
    public JSONArray getAllRoleForXm(Long userId) {
        // Query All Roles
        List<SysRole> list = roleMapper.selectList(null);
        List<SysRoleUser> roleUserList = roleUserService.getRoleUserByUserId(userId);
        JSONArray jsonArray = new JSONArray();
        if(!CollectionUtils.isEmpty(list)) {
            for(SysRole role : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", role.getRoleName());
                jsonObject.put("id", role.getId());
                if(!CollectionUtils.isEmpty(roleUserList) && roleUserList.get(0).getRoleId().equals(role.getId())) {
                    jsonObject.put("selected", true);
                }
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    @Override
    public List<SysRole> getRoleByUserId(Long userId) {
        return roleMapper.getRoleByUserId(userId);
    }

    @Override
    public List<GrantedAuthority> getRolesByUser(Long userId) {
        Set<String> permissions = new HashSet<>();
        List<SysRole> roleList = roleMapper.getRoleByUserId(userId);
        if(!CollectionUtils.isEmpty(roleList)) {
            for(SysRole sysRole : roleList) {
                permissions.add(sysRole.getRoleCode());
            }
        }
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     * @Description Check Role Bind User
     * @Param [roleId]
     * @Return void
     */
    private void checkRoleUser(Long roleId) {
        List<SysRoleUser> roleUsers = roleUserService.getRoleUserByRoleId(roleId);
        if(!CollectionUtils.isEmpty(roleUsers)) {
            throw new BadRequestException("the role has been bound to a user, please unbind it before deleting it");
        }
    }

    /**
     * @Description Check Role Name and Code
     * @Param [role]
     * @Return void
     */
    public void checkRole(SysRole role) {
        List<SysRole> roles;
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if(role.getId() != null) {
            wrapper.ne("id", role.getId());
        }
        wrapper.and(w -> w.eq("role_name", role.getRoleName()).or().eq("role_code", role.getRoleCode()));
        roles = roleMapper.selectList(wrapper);
        if(roles != null && !roles.isEmpty()) {
            throw new BadRequestException("role name or code exist, input again");
        }
    }
}
