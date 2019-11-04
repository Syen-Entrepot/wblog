package com.ws.dao;

import com.ws.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Syen
 * @date 2019/10/31 0031-下午 14:04
 */
public interface TagsRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);

}
