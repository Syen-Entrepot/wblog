package com.ws.service.impl;

import com.ws.pojo.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Syen
 * @date 2019/11/26 0026-下午 16:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceImplTest {

    private static long a = 4;

    @Autowired
    private CommentServiceImpl commentService;

    @Test
    public void listCommentByBlogId() {
        List<Comment> commentList = commentService.listCommentByBlogId(a);
      // System.out.println(commentList);
    }

}