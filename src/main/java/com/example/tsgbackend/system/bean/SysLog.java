package com.example.tsgbackend.system.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_log")
public class SysLog {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String logType;

    private String method;

    private String params;

    private long time;

    private String ip;

    private String username;

    private String exceptionDetail;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private LocalDateTime createTime;

    public SysLog(String logType, long time) {
        this.logType = logType;
        this.time = time;
    }
}
