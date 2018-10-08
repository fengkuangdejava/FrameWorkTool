package com.onescorpion.nova.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 拦截器 利用springAop执行请求进入方法前的动作  权限控制请用过滤器，不要用拦截器
 *
 *@author 李挺 【fengkuangdejava@outlook.com】
 *@date 2018/10/8 10:09
 */
@Aspect //声明这个是切面类
@Component //加入Spring容器管理
public class RequestAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestAspect.class);

    @Pointcut("execution(public * com.onescorpion.nova.controller.*.*(..))")//切入点  正则匹配对应方法          方法修饰符+包名+类名+方法名+（参数类型+参数）
    public void shareMethod(){}

    @Before("shareMethod()")
    public void doBefore(JoinPoint joinPoint){
        logger.info("methods start");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //url
        logger.info("url={}",request.getRequestURL());
        //method
        logger.info("method={}",request.getMethod());
        //address
        logger.info("adr={}",request.getRemoteAddr());
        //类方法
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        //param
 /*       logger.info("args={}",joinPoint.getArgs());*/
    }
    @After("shareMethod()")
    public void doAfter(){
    }//在 执行符合切入点的方法之后 所要执行的操作

    @AfterReturning(returning = "object",pointcut = "shareMethod()")//在 执行符合切入点的带返回值得方法return之后 所要执行的操作
    public void doAfterReturnning(Object object){
        logger.info("response={}",object.toString());
    }
}
