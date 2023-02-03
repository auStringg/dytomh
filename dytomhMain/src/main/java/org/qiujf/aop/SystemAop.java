package org.qiujf.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class SystemAop {

    @Pointcut("within(org.qiujf.redis..*)")
    public void pointcut(){}

    @Before("pointcut()")
    public void globalBefore(JoinPoint joinPoint){
        System.out.println("before--------------，打印入参：" + Arrays.toString(joinPoint.getArgs()));
    }
}
