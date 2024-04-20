package com.example.tsgbackend.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.tsgbackend.common.exception.BadRequestException;
import com.example.tsgbackend.common.utils.StringUtil;
import com.example.tsgbackend.logs.annotation.Log;
import com.example.tsgbackend.system.bean.SysLog;
import com.example.tsgbackend.system.bean.dto.QueryDto;
import com.example.tsgbackend.system.mapper.SysLogMapper;
import com.example.tsgbackend.system.service.SysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl implements SysLogService {

    private final SysLogMapper logMapper;

    @Override
    public void save(String username, String ip, ProceedingJoinPoint joinPoint, SysLog sysLog) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log aopLog = method.getAnnotation(Log.class);

        // method url
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";
        StringBuilder params = new StringBuilder("{");

        // params
        List<Object> argValues = new ArrayList<>(Arrays.asList(joinPoint.getArgs()));

        // param name
        for(Object argValue : argValues) {
            params.append(argValue).append(" ");
        }

        // description
        if(sysLog != null) {
            sysLog.setDescription(aopLog.value());
        }
        assert sysLog != null;
        sysLog.setIp(ip);

        // if is log in
        String loginPath = "login";
        if(loginPath.equals(signature.getName())) {
            try {
                username = JSON.parseObject(JSONObject.toJSONString(argValues.get(0))).getString("username");
            } catch (BadRequestException e) {
                SysLogServiceImpl.log.error(e.getMessage(), e);
            }
        }
        sysLog.setMethod(methodName);
        sysLog.setUsername(username);
        sysLog.setParams(params + "}");

        if(sysLog.getId() == null) {
            logMapper.insert(sysLog);
        } else {
            logMapper.updateById(sysLog);
        }
    }

    @Override
    public IPage<SysLog> getLogList(QueryDto queryDto, String logType) {
        LambdaQueryWrapper<SysLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysLog::getCreateTime);
        if(StringUtil.isNotBlank(logType)) {
            wrapper.eq(SysLog::getLogType, logType);
        }
        if(StringUtil.isNotBlank(queryDto.getBlurry())) {
            wrapper.and(q -> q.like(SysLog::getUsername, queryDto.getBlurry())
                    .or().like(SysLog::getDescription, queryDto.getBlurry()));
        }
        Page<SysLog> page = new Page<>();
        page.setSize(queryDto.getSize());
        page.setCurrent(queryDto.getCurrentPage());
        return logMapper.selectPage(page, wrapper);
    }
}
