package com.example.tsgbackend.system.controller;

import com.example.tsgbackend.common.constant.CaptchaConstants;
import com.example.tsgbackend.common.constant.SecurityConstants;
import com.example.tsgbackend.common.exception.BadRequestException;
import com.example.tsgbackend.common.utils.JwtUtil;
import com.example.tsgbackend.common.utils.ResultUtil;
import com.example.tsgbackend.common.utils.StringUtil;
import com.example.tsgbackend.config.config.CacheConfig;
import com.example.tsgbackend.config.security.JwtUser;
import com.example.tsgbackend.logs.annotation.Log;
import com.example.tsgbackend.system.bean.SysRole;
import com.example.tsgbackend.system.bean.SysUser;
import com.example.tsgbackend.system.bean.dto.UserDto;
import com.example.tsgbackend.system.service.SysRoleService;
import com.example.tsgbackend.system.service.SysUserService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController extends ResultUtil {

    private final SysUserService userService;

    private final SysRoleService roleService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final CacheConfig cacheConfig;

    @Log("User Login")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDto userDto, HttpServletRequest request) {
        try {
            // check captcha
            if(StringUtil.isBlank(userDto.getCode()) || !checkCode(userDto.getUuid(), userDto.getCode())) {
                return fail(false, "incorrect captcha");
            }
            // check user exist by username
            SysUser user = userService.findByName(userDto.getUsername());
            if(user == null) {
                return fail(false, "incorrect username or password");
            }
            // check password
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getCode());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            // set authentication info into context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // get user role
            List<String> roles = getRolesByUserId(user.getId());
            // retake token
            String token = JwtUtil.generateToken(user.getUsername(), roles, false);
            // generate refresh token
            String refreshToken = JwtUtil.getRefreshToken(user.getUsername());

            // user info
            userDto.setEmail(user.getEmail());
            userDto.setNickName(user.getNickname());
            userDto.setRoles(roles);
            userDto.setPassword("******");

            return success(true, new JwtUser(token, refreshToken, userDto));
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return fail(false, e.getMessage());
        }
    }

    /**
     * @description new user sign in
     * @param userDto user dto
     * @return org.springframework.http.ResponseEntity
     */
    @Log("User Register")
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserDto userDto) {
        try {
            userService.editUser(userDto);
            return success(true, "sign in success");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    @PutMapping("/refresh")
    public ResponseEntity<Object> refreshToken(HttpServletRequest request) {
        try {
            String refreshToken = request.getHeader(SecurityConstants.TOKEN_HEADER);
            // if refresh token exist
            if(StringUtil.isNotBlank(refreshToken)) {
                // remove header
                refreshToken = refreshToken.replaceFirst(SecurityConstants.TOKEN_PREFIX, "");
                Claims claims = JwtUtil.getRefreshTokenBody(refreshToken);
                System.out.println(claims.getSubject());

                // if refresh token is not expired
                if(claims.get("exp", Long.class) > 0) {
                    // get current user info
                    SysUser user = userService.findByName(claims.getSubject());
                    // get current user role
                    List<String> roles = getRolesByUserId(user.getId());
                    // retake token
                    String token = JwtUtil.generateToken(user.getUsername(), roles, false);
                    return success(true, token);
                }
            }
        } catch (BadRequestException e) {
            return fail(false, e.getMsg());
        }
        return fail(false, "please login again");
    }

    @GetMapping("/code")
    public ResponseEntity<Object> getVerifyCode() {
        try {
            Captcha captcha = new ArithmeticCaptcha(CaptchaConstants.width, CaptchaConstants.height);
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String captchaValue = captcha.text();
            // solve exception that caption value is float type
            if(captchaValue.contains(".")) {
                captchaValue = captchaValue.split("\\.")[0];
            }
            // save captcha info for 1 minute
            cacheConfig.put(uuid, captchaValue, 1);
            // captcha info
            Map<String, Object> imgResult = new HashMap<String, Object>(2) {
                {
                    put("img", captcha.toBase64());
                    put("uuid", uuid);
                }
            };
            return ResponseEntity.ok(imgResult);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return fail(false, e.getMsg());
        }
    }

    /**
     * @description check captcha
     * @param uuid uuid
     * @param code code
     * @return boolean
     */
    private boolean checkCode(String uuid, String code) {
        boolean r = false;
        if(cacheConfig.get(uuid) != null && cacheConfig.get(uuid).equals(code)) {
            r = true;
            cacheConfig.invalidate(uuid);
        }
        return r;
    }

    /**
     * @description get current user role list
     * @param userId current user id
     * @return java.util.List
     */
    private List<String> getRolesByUserId(Long userId) {
        // get current user role object
        List<SysRole> sysRoles = roleService.getRoleByUserId(userId);
        // role
        List<String> roles = new ArrayList<>();
        sysRoles.forEach( sysRole -> {
            roles.add(sysRole.getRoleCode());
        });
        return roles;
    }
}
