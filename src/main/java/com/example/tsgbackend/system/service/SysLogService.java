package com.example.tsgbackend.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.tsgbackend.system.bean.SysLog;
import com.example.tsgbackend.system.bean.dto.QueryDto;
import org.aspectj.lang.ProceedingJoinPoint;

public interface SysLogService {

    /**
     *
     * @param username username
     * @param ip ip address of user "username"
     * @param joinPoint joint point in AOP, a time point during execution
     * @param sysLog system log entity
     */
    void save(String username, String ip, ProceedingJoinPoint joinPoint, SysLog sysLog);

    /**
     *
     * @param queryDto query data transfer object
     * @param logType log type
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     */
    IPage<SysLog> getLogList(QueryDto queryDto, String logType);
}
