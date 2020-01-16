package com.ws.service;

import com.ws.pojo.Comment;

import java.util.List;

/**
 * @author wusen
 * @date 2019/11/22 0022-下午 13:48
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
