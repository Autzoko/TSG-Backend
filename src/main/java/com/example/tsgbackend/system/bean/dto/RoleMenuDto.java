package com.example.tsgbackend.system.bean.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleMenuDto {
    private Long roleId;
    private Set<Long> menuIds;
}
