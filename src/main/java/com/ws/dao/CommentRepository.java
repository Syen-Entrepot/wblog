package com.ws.dao;

import com.ws.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wusen
 * @date 2019/11/22 0022-下午 13:54
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId,Sort sort);

    @Transactional//更新加上事务
    @Modifying//更新数据加这个注解
    @Query("DELETE from Comment c where c.id =?1")
    void deleteByParentCommentId(Long id);
}
