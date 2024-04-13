package com.example.tsgbackend.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.tsgbackend.system.bean.SysUser;
import com.example.tsgbackend.system.bean.dto.UserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * @Description: Get User From Username
     * @Param: [username]
     * @Return: com.example.tsgbackend.system.bean.dto.UserDto
     */

    UserDto loadByName(@Param("username") String username);

    /**
     * @Description: User List
     * @Param: [blurry]
     * @Return: java.util.List<com.example.tsgbackend.system.bean.dto.UserDto>
     */
    IPage<UserDto> queryUserTable(Page<?> page, @Param("blurry") String blurry);
}
