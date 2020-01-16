package com.ws.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wusen
 * @date 2019/10/28 0028-下午 13:08
 */
@ControllerAdvice//可以拦截所有含有@controller注解的类
public class ControllerExceptionHandler {

    //获取日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    * @ExceptionHandler:---标注这个类可以做异常处理的
    * Exception.class:---是Exception级别的类
    * */
    @ExceptionHandler(Exception.class)
    public ModelAndView exception(HttpServletRequest request,Exception e) throws Exception {
        logger.error("Request URL : {},Exception : {}",request.getRequestURL(),e);

        if(AnnotationUtils.findAnnotation(e.getClass(),ResponseStatus.class) != null){
             throw e;
        }//排除使用ResponseStatus注解的类

        ModelAndView modelAndView =new ModelAndView();
        modelAndView.addObject("requestUrl",request.getRequestURL());
        modelAndView.addObject("exception",e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

}
