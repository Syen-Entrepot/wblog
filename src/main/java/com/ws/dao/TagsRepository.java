package com.ws.dao;

import com.ws.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wusen
 * @date 2019/10/31 0031-下午 14:04
 */
public interface TagsRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);

    //@Query("select t from Tag t")
    @Query(nativeQuery = true,value = "SELECT distinct t.id,t.name FROM w_tag t,w_blog b,w_blog_tags tb WHERE t.id=tb.tags_id and b.id=tb.blogs_id and b.polished = true ORDER BY t.id desc LIMIT 100")
    List<Tag> findTop();

}
