package com.ws.service;

import com.ws.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @author Syen
 * @date 2019/10/31 0031-下午 13:53
 */
public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);//通过名字查找---防止数据库中有重复的name

    Page<Tag> listTag(Pageable pageable);//分页查询

    List<Tag> ListTag();//返回所有数据

    Tag updateTag(Long id, Tag type);//修改分类

    void deleteTag(Long id);//删除分类
}
