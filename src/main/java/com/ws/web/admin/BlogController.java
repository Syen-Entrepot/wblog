package com.ws.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Syen
 * @date 2019/10/30 0030-下午 19:28
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @GetMapping("/blogs")
    public String blogs(){

        return "admin/blogs";
    }
}
