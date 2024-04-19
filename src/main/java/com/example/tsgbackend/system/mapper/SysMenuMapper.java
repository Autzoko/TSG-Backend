package com.example.tsgbackend.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tsgbackend.system.bean.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * @Description Get Menu Tree
     * @Param [roles]
     * @Return java.util.List<com.example.tsgbackend.system.bean.SysMenu>
     */
    List<SysMenu> getMenuTree(@Param("roles") List<String> roles);

    /**
     * @Desciption Get URL from Role
     * @Param [roles]
     * @Return java.util.List<java.lang.String>
     */
    List<String> getMenuUrlByRole(@Param("roles") List<String> roles);

    /**
     * @Description Get Permission List
     * @Param [roles]
     * @Return java.util.List<java.lang.String>
     */
    List<String> getPermission(@Param("roles") List<String> roles);
}
