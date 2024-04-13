package com.example.tsgbackend.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.tsgbackend.system.bean.SysRoleUser;
import com.example.tsgbackend.system.mapper.SysRoleUserMapper;
import com.example.tsgbackend.system.service.SysRoleUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysRoleUserServiceImpl implements SysRoleUserService {
    private final SysRoleUserMapper sysRoleUserMapper;

    @Override
    public List<SysRoleUser> getRoleUserByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleUser::getRoleId, roleId);
        return sysRoleUserMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public void edit(Long userId, List<String> roleIds) {
        LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleUser::getUserId, userId);
        sysRoleUserMapper.delete(wrapper);

        roleIds.forEach(role -> {
            SysRoleUser roleUser = new SysRoleUser();
            roleUser.setUserId(userId);
            roleUser.setRoleId(Long.parseLong(role));
            sysRoleUserMapper.insert(roleUser);
        });
    }

    @Override
    public List<SysRoleUser> getRoleUserByUserId(Long userId) {
        LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleUser::getUserId, userId);
        return sysRoleUserMapper.selectList(wrapper);
    }

    @Override
    public void deleteByUserId(String userId) {
        LambdaQueryWrapper<SysRoleUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleUser::getUserId, userId);
        sysRoleUserMapper.delete(wrapper);
    }
}
