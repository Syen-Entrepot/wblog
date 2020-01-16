package com.ws.aspects;

import com.ws.aspectpojo.AspectPojo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wusen
 * @date 2019/10/28 0028-下午 14:31
 */
@Aspect//定义此类为切面
@Component//声明此类为组件，让其扫描到bean库中
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    @Pointcut:----声明此方法为切面
    execution(): -----要切那些方法
    第一个*:是指所有的权限----如：public priva等
    然后是包名:com.ws.web
    第二个*:------web包下的所有类
    第三个*：---是类里的所有方法
    (..):-----方法的任何类型的参数--如：int，float，double等

     * */
    @Pointcut("execution(* com.ws.web.*.*(..))")
    public void log(){

    }

/*
* JoinPoint：---切面里的类---用来获取被切的类和此类的方法
* */
    @Before("log()")//表示doBefore方法在log（）方法之前执行
    public void doBefore(JoinPoint joinPoint){//在切面之前执行
        //用ServletRequestAttributes来获取httpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //在赋值给一个httpServletRequest的对象
        HttpServletRequest httpServletRequest = attributes.getRequest();
        //再将httpServletRequest对象的数据分别赋值给其它的参数
        String url = httpServletRequest.getRequestURL().toString();//获取http中的URL
        String ip = httpServletRequest.getRemoteAddr();//获取http中的IP地址
        //获取被切的类的类名和方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        //获取请求的参数
        Object[] args = joinPoint.getArgs();
        //将上面的参传到aspectpojo对象的构造器中
        AspectPojo aspectPojo = new AspectPojo(url,ip,classMethod,args);
        //传到日志logger中打印
        logger.info("Request : {}",aspectPojo);
    }

    @After("log()")//表示doAfter方法在log（）方法之后执行
    public void doAfter(){
      //  logger.info("--------doAfter------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result : {}",result);//返回的是indexcontroller里的index方法返回的index
    }
}
