package com.ws.dao;

import com.ws.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wusen
 * @date 2019/11/22 0022-下午 13:54
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogId(Long blogId,Sort sort);
}
