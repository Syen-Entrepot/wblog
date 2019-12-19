package com.ws.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Syen
 * @date 2019/12/19 0019-下午 14:40
 */
@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String about(){

        return "about";
    }

}
