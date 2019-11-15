package com.ws.service;

import com.ws.pojo.Blog;
import com.ws.vo.BlogQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

/**
 * @author Syen
 * @date 2019/11/4 0004-下午 13:58
 */
public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable,BlogQuery blogQuery);

    Page<Blog> listBlog(Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);


}
