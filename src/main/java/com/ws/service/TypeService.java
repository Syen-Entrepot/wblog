package com.ws.service;

import com.ws.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @author Syen
 * @date 2019/10/31 0031-下午 13:53
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);//通过名字查找---防止数据库中有重复的name

    Page<Type> listType(Pageable pageable);//分页查询

    List<Type> ListType();//返回所有数据

    Type updateType(Long id,Type type);//修改分类

    void deleteType(Long id);//删除分类
}
