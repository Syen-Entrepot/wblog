package com.ws.dao;

import com.ws.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wusen
 * @date 2019/11/4 0004-下午 14:05
 */
public interface BlogRepository extends JpaRepository<Blog,Long>,JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true and b.polished = true ")
    List<Blog> findTop(Pageable pageable);

    //正常的mysql(select * from w_blog where title like '%内容%')
    @Query("select b from Blog b where (b.title like ?1 or b.content like ?1) and b.polished = true")//这里的1是指第一个参数String query
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Transactional//更新加上事务
    @Modifying//更新数据加这个注解
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1 and b.polished = true")
    List<Blog> findByYear(String year);

    @Query("select b from Blog b where b.polished = 1")
    Page<Blog> findAllExcept(Pageable pageable);

    @Query(nativeQuery = true,value = "select count(*) from w_blog b where b.polished = true")
    long count1();

}
