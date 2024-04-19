package com.example.tsgbackend.system.bean.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuDto {

    private Long id;
    private String title;
    private String url;
    private String parentId;
    private int sort;
    private String type;
    private String permission;

}
