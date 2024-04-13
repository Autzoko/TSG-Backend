package com.example.tsgbackend.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;
    private static final List<CallBack> CALL_BACKS = new ArrayList<>();
    private static boolean addCallback = true;

    /**
     * @Description fetch bean from applicationContext and automatically convert to the type of assigning object
     * @Param [requiredType]
     * @Return T
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjection();
        return applicationContext.getBean(requiredType);
    }

    /**
     * @Description verify application context
     * @Param []
     * @Return void
     */
    private static void assertContextInjection() {
        if(applicationContext == null) {
            throw new IllegalStateException(
                    "application context attribute not injected, " +
                            "please register SpringContextHolder in Springboot starter"
            );
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringContextHolder.applicationContext != null) {
            log.info("the ``ApplicationContext`` in the SpringContextHolder has been covered, " +
                    "the original ApplicationContext is: " + SpringContextHolder.applicationContext);
        }
        SpringContextHolder.applicationContext = applicationContext;
        if(addCallback) {
            for(CallBack callBack : SpringContextHolder.CALL_BACKS) {
                callBack.executor();
            }
        }
        SpringContextHolder.addCallback = false;
    }

    @Override
    public void destroy() {
        log.info("cleaning ApplicationContext in SpringContextHolder: " + applicationContext);
        applicationContext = null;
    }
}
