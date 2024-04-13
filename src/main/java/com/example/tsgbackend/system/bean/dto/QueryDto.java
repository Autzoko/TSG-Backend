package com.example.tsgbackend.system.bean.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryDto {
    private String blurry;

    private long currentPage = 1;

    private long size = 10;
}
