package com.ws.web.admin;

import com.ws.pojo.User;
import com.ws.service.BlogService;
import com.ws.service.TypeService;
import com.ws.service.UserService;
import com.ws.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author wusen
 * @date 2019/10/30 0030-下午 15:39
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping
    public String loginPage(){

        return "redirect:/";
    }

    //登录
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes,
                        Model model,
                        BlogQuery blogQuery,
                        HttpSession httpSession,
                        @PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable){
        User user = userService.checkUser(username,password);
        if(user != null){
            user.setPassword(null);//不要把用户的密码返回
            session.setAttribute("user",user);
            session.setAttribute("email",user.getEmail());
            model.addAttribute("types",typeService.ListType());
            model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
            httpSession.setAttribute("hide","hide");
            httpSession.setAttribute("show","show");
            return "admin/blogs";
        }else {
            //重定向拿不到model或modelAndView里面的值，要用RedirectAttributes
            attributes.addFlashAttribute("message","用户名或密码错误!");
            return "redirect:/";
        }

    }

    //退出
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("hide","show");
        session.removeAttribute("user");//清空session
        session.removeAttribute("email");
        return "redirect:/";
    }
}
