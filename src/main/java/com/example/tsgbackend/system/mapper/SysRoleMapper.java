package com.example.tsgbackend.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tsgbackend.system.bean.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * @Description Get Role by User Id
     * @Param [userId]
     * @Return java.util.List<com.example.tsgbackend.system.bean.SysRole>
     */
    List<SysRole> getRoleByUserId(@Param("userId") Long userId);
}
