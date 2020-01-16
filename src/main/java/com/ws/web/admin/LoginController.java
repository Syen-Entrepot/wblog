package com.ws.web.admin;

import com.ws.pojo.User;
import com.ws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public String loginPage(){

        return "admin/login";
    }

    //登录
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if(user != null){
            user.setPassword(null);//不要把用户的密码返回
            session.setAttribute("user",user);
            return "admin/index";
        }else {
            //重定向拿不到model或modelAndView里面的值，要用RedirectAttributes
            attributes.addFlashAttribute("message","用户名或密码错误!");
            return "redirect:/admin";
        }

    }

    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");//清空session
        return "redirect:/admin";
    }
}
