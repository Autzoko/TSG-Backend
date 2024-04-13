package com.example.tsgbackend.system.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.tsgbackend.common.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_role_menu")
public class SysRoleMenu extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long menuId;

    private Long roleId;
}
