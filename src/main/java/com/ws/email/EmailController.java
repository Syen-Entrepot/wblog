package com.ws.email;

import com.ws.service.BlogService;
import com.ws.service.TagService;
import com.ws.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;

/**
 * @author wusen
 * @date 2020/2/6 0006-下午 12:44
 */
@Controller
public class EmailController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private EmailService emailService;

    private String checkCode;

    private String clientEmail;

    private Date CURRENT_TIME;

    private Date LAST_TIME;


//    @GetMapping("/email")
////    public String index(){
////        return "login_logout";
////    }

    @PostMapping("/getCheckCode")
    @ResponseBody
    public String getCheckCode(String email){
        checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        clientEmail = email;
        String message = "您的注册验证码为："+checkCode;
        String bb = clientEmail + message;
        try {
            emailService.sendSimpleMail(email, "注册验证码", message);
            CURRENT_TIME = new Date();
        }catch (Exception e){
            //e.printStackTrace();
            return bb;
        }
        return checkCode;
    }
    /**
     * 验证码校验
     */
    @PostMapping("/CheckEmailCode")
    public String CheckEmailCode(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                                 Model model, String code, HttpSession httpSession) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        LAST_TIME = new Date();
        if(((LAST_TIME.getTime() - CURRENT_TIME.getTime()) /1000 ) <= 40){
            if(code != null){
                if (code.equals(checkCode)) {
                    httpSession.setAttribute("email", clientEmail);
                    httpSession.setAttribute("hide","hide");
                    httpSession.setAttribute("show","show");
                    return "index";
                } else {
                    httpSession.setAttribute("show","hide");
                    httpSession.setAttribute("hide","show");
                    model.addAttribute("error", "验证码错误！");
                    return "index";
                }
            }else {
                httpSession.setAttribute("show","hide");
                httpSession.setAttribute("hide","show");
                model.addAttribute("error","请输入验证码!");
                return "index";
            }
        }else {
            model.addAttribute("error","验证码超时!");
            return "index";
        }



    }
    /**
     * 游客退出登录
     */
    @GetMapping("/logout")
    public String logout(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         Model model,HttpSession httpSession){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        httpSession.removeAttribute("email");
        httpSession.removeAttribute("hide");
        httpSession.removeAttribute("show");
        return "index";
    }


}
