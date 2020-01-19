package com.ws.web;

import com.ws.pojo.Comment;
import com.ws.pojo.User;
import com.ws.service.BlogService;
import com.ws.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author wusen
 * @date 2019/11/22 0022-下午 13:37
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog::comment_list";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user =(User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
           // comment.setNickname(user.getNickname());
        }else {
            comment.setAvatar(avatar);
        }

        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}
