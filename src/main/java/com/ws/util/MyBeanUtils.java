package com.ws.util;

/**
 * @author wusen
 * @date 2019/11/15 0015-下午 15:20
 */

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * 过滤掉空的属性,就是空的属性按默认的
 * */
public class MyBeanUtils {

    public static String[] getNullPropertyNames(Object source){
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertylist = new ArrayList<>();
        for(PropertyDescriptor pd : pds){
            String propertyName = pd.getName();
            if(beanWrapper.getPropertyValue(propertyName) == null){
                nullPropertylist.add(propertyName);
            }
        }
        return nullPropertylist.toArray(new String[nullPropertylist.size()]);
    }

}
