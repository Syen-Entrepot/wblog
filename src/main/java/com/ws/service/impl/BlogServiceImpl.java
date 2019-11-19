package com.ws.service.impl;

import com.ws.dao.BlogRepository;
import com.ws.exception.NotFoundException;
import com.ws.pojo.Blog;
import com.ws.pojo.Type;
import com.ws.service.BlogService;
import com.ws.util.MarkdownUtils;
import com.ws.util.MyBeanUtils;
import com.ws.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Syen
 * @date 2019/11/4 0004-下午 14:04
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blogQuery.getTitle()) && blogQuery.getTitle() != null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blogQuery.getTitle()+"%"));
                }
                if(blogQuery.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blogQuery.getTypeId()));
                }
                if(blogQuery.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blogQuery.isRecommend()));
                }
                /**
                 * list转换成数组
                 */
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null){//新增博客
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);//新增博客的浏览记录
        }else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if(blog == null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();//因为hibernate的数据修改后会回传到数据库，所以保证数据库的content是markdown语法的content
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).orElse(null);
        if(b == null){
            throw  new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b,MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
