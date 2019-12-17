package com.ws.service.impl;

import com.ws.dao.CommentRepository;
import com.ws.pojo.Comment;
import com.ws.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Syen
 * @date 2019/11/22 0022-下午 13:51
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");//根据时间进行排序
        return commentRepository.findByBlogId(blogId,sort);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();//前端默认的-1
        if(parentCommentId != -1){
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
