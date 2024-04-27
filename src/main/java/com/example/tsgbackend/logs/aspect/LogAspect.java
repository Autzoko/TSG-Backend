package com.example.tsgbackend.logs.aspect;

import com.example.tsgbackend.common.utils.RequestHolder;
import com.example.tsgbackend.common.utils.SecurityUtil;
import com.example.tsgbackend.common.utils.StringUtil;
import com.example.tsgbackend.common.utils.ThrowsUtil;
import com.example.tsgbackend.system.bean.SysLog;
import com.example.tsgbackend.system.service.SysLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
@Component
public class LogAspect {

    private final SysLogService sysLogService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public LogAspect(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    /**
     * @description config cut point
     */
    @Pointcut("@annotation(com.example.tsgbackend.logs.annotation.Log)")
    public void loginPointCut() {}

    /**
     * @description config around info, use authorized cut point on method logPointCut
     * @param joinPoint a join point
     * @return Object
     * @throws Throwable
     */
    @Around("loginPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        SysLog sysLog = new SysLog("1", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        sysLogService.save(getUsername(), StringUtil.getIp(request), (ProceedingJoinPoint) joinPoint, sysLog);
        return result;
    }

    @AfterThrowing(pointcut = "loginPointCut()", throwing = "e")
    @Transactional(rollbackFor = Exception.class)
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        SysLog sysLog = new SysLog("2", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        sysLog.setExceptionDetail(ThrowsUtil.getStackTrace(e));
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        sysLogService.save(getUsername(), StringUtil.getIp(request), (ProceedingJoinPoint) joinPoint, sysLog);
    }

    /**
     * @description get current username
     * @return java.lang.String
     */
    public String getUsername() {
        try {
            return SecurityUtil.getCurrentUsername();
        } catch(Exception e) {
            return "";
        }
    }


}
