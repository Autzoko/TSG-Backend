package com.example.tsgbackend.config.security;

import com.example.tsgbackend.common.exception.BadRequestException;
import com.example.tsgbackend.system.bean.dto.JwtUserDto;
import com.example.tsgbackend.system.bean.dto.UserDto;
import com.example.tsgbackend.system.service.SysRoleService;
import com.example.tsgbackend.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserService userService;

    private final SysRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        boolean searchDb = true;
        JwtUserDto jwtUserDto = null;

        if(searchDb) {
            UserDto user;
            try {
                user = userService.loadByName(username);
            } catch (BadRequestException e) {
                throw new UsernameNotFoundException("username or password incorrect");
            }

            if(user == null) {
                throw new UsernameNotFoundException("username or password incorrect");
            } else {
                if(!user.getEnabled()) {
                    throw new BadRequestException("account not enabled");
                }
                jwtUserDto = new JwtUserDto(
                        user,
                        roleService.getRolesByUser(user.getId())
                );
            }
        }
        return jwtUserDto;
    }
}