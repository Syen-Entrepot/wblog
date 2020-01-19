package com.ws.service.impl;

import com.ws.dao.CommentRepository;
import com.ws.pojo.Comment;
import com.ws.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wusen
 * @date 2019/11/22 0022-下午 13:51
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort(Sort.Direction.ASC,"createTime");//根据时间进行排序
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
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

    /**
     * 循环每个顶级的评论点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for(Comment comment : comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的个层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }
    private void combineChildren(List<Comment> comments){
        for(Comment comment : comments){
            List<Comment> reply = comment.getReplayComments();
            for(Comment reply1 : reply){
                //循环迭代，找出子代，存放在temReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplayComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }
    //存放迭代找出所有的子代集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment){
        tempReplys.add(comment);//顶级节点添加到临时存放集合
        if(comment.getReplayComments().size()>0){
            List<Comment> replys = comment.getReplayComments();
            for (Comment reply2:replys){
                tempReplys.add(reply2);
                if(reply2.getReplayComments().size()>0){
                    recursively(reply2);
                }
            }
        }
    }
}
