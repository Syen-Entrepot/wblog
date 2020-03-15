package com.ws.web;

import com.ws.pojo.Comment;
import com.ws.pojo.User;
import com.ws.service.BlogService;
import com.ws.service.CommentService;
import com.ws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    private UserService userService;

    @Value("${comment.avatar}")
    private String avatar;

    private static String admin_username = "syen";

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
        }else {
            comment.setAvatar(avatar);
        }

        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

    /**
     * 删除评论
     * @param comment
     * @param session
     * @return
     */

    @PostMapping("/comment_del")
    public String comment_del(Comment comment, HttpSession session, RedirectAttributes attributes){
        Long commentId = comment.getId();
        Long blogId = comment.getBlog().getId();
        String commentEmail = comment.getEmail();
        String adminEmail = userService.checkUserEmail(admin_username);
            if(adminEmail.equals(session.getAttribute("email"))){
                commentService.setCommentPrant_comment_id(commentId);
                try{
                    Thread.sleep(1000);
                    commentService.deleteComment(commentId);
                }catch (Exception e){
                    attributes.addFlashAttribute("delmessage","删除失败");
                    return "redirect:/comments/" + blogId;
                }
            }else{
                if(commentEmail.equals(session.getAttribute("email"))){
                    commentService.setCommentPrant_comment_id(commentId);
                    try{
                        Thread.sleep(1000);
                        commentService.deleteComment(commentId);
                    }catch (Exception e){
                        attributes.addFlashAttribute("delmessage","删除失败");
                        return "redirect:/comments/" + blogId;
                    }
                }else {
                    attributes.addFlashAttribute("delmessage","你没有权限");
                    return "redirect:/comments/" + blogId;
                }
            }


        return "redirect:/comments/" + blogId;
    }

}
