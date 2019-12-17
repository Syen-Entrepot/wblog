package com.ws.service;

import com.ws.pojo.Blog;
import com.ws.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Syen
 * @date 2019/11/4 0004-下午 13:58
 */
public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);//用于获取blog并做markdown与html的转换

    Page<Blog> listBlog(Pageable pageable,BlogQuery blogQuery);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);


}
