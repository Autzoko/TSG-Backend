package com.example.tsgbackend.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.tsgbackend.common.constant.CommonConstants;
import com.example.tsgbackend.common.exception.BadRequestException;
import com.example.tsgbackend.common.utils.SecurityUtil;
import com.example.tsgbackend.common.utils.StringUtil;
import com.example.tsgbackend.system.bean.SysUser;
import com.example.tsgbackend.system.bean.dto.QueryDto;
import com.example.tsgbackend.system.bean.dto.UserDto;
import com.example.tsgbackend.system.mapper.SysUserMapper;
import com.example.tsgbackend.system.service.SysRoleUserService;
import com.example.tsgbackend.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {
    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final SysRoleUserService roleUserService;

    @Override
    public SysUser findByName(String userName) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, userName);
        return sysUserMapper.selectOne(wrapper);
    }

    @Override
    public void editUser(UserDto userDto) throws BadRequestException {
        checkUser(userDto);
        SysUser user = new SysUser();
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setId(userDto.getId());
        user.setNick_name(userDto.getNickName());

        if(userDto.getEnabled() != null) {
            user.setEnabled(userDto.getEnabled());
        }

        if(user.getId() != null) {
            sysUserMapper.updateById(user);
        } else {
            // init user password, default: 111111
            user.setPassword(passwordEncoder.encode(CommonConstants.DEFAULT_PASSWORD));
            sysUserMapper.insert(user);
        }

        // modify role if it has role
        if(!CollectionUtils.isEmpty(userDto.getRoleIds()) & user.getId() != null) {
            roleUserService.edit(user.getId(), userDto.getRoleIds());
        }
    }

    @Override
    public IPage<UserDto> queryUserTable(QueryDto queryDto) {
        Page<UserDto> page = new Page<>();
        page.setCurrent(queryDto.getCurrentPage());
        page.setSize(queryDto.getSize());
        return sysUserMapper.queryUserTable(page, queryDto.getBlurry());
    }

    @Override
    public UserDto loadByName(String userName) {
        return sysUserMapper.loadByName(userName);
    }

    @Override
    @Transactional(rollbackFor = BadRequestException.class)
    public void delUser(String id) {
        roleUserService.deleteByUserId(id);
        sysUserMapper.deleteById(id);
    }

    @Override
    public void enabledUser(SysUser sysUser) {
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public void updatePassword(JSONObject jsonObject) {
        String password = jsonObject.getString("password");
        String newPassword = jsonObject.getString("newPassword");
        String confirmPassword = jsonObject.getString("confirmPassword");

        // Get current user
        SysUser user = sysUserMapper.selectById(SecurityUtil.getCurrentUserId());
        String pwd = user.getPassword();

        if(!passwordEncoder.matches(password, pwd)) {
            throw new BadRequestException("original password incorrect, input again");
        }

        if(!newPassword.equals(confirmPassword)) {
            throw new BadRequestException("new password not equals to the confirm password, input again");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        sysUserMapper.updateById(user);
    }

    /**
     * @Description Verify Username and Nickname
     * @Param [userDto]
     * @Return void
     */
    private void checkUser(UserDto userDto) throws BadRequestException {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        if(userDto.getId() != null) {
            wrapper.ne(SysUser::getId, userDto.getId());
        }
        if(StringUtil.isNotBlank(userDto.getUsername()) && StringUtil.isNotBlank(userDto.getNickName())) {
            wrapper.and(w -> w.eq(SysUser::getUsername, userDto.getUsername()).or().eq(SysUser::getNick_name, userDto.getNickName()));
        }
        long count = sysUserMapper.selectCount(wrapper);
        if(count > 0) {
            throw new BadRequestException("username or nickname exists, please input new one");
        }
    }
}
