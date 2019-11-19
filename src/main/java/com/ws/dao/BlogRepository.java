package com.ws.dao;

import com.ws.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Syen
 * @date 2019/11/4 0004-下午 14:05
 */
public interface BlogRepository extends JpaRepository<Blog,Long>,JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true ")
    List<Blog> findTop(Pageable pageable);

    //正常的mysql(select * from w_blog where title like '%内容%')
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")//这里的1是指第一个参数String query
    Page<Blog> findByQuery(String query,Pageable pageable);

}
