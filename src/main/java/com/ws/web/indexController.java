package com.ws.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Syen
 * @date 2019/10/28 0028-下午 12:36
 */
@Controller
public class indexController {

    @GetMapping("/{id}/{name}")
    public String index(@PathVariable Integer id,@PathVariable String name){
       // int i=9/0;
      /*  String wblog = null;
        if(wblog == null){
            throw  new NotFoundException("博客不存在");
        }*/
      System.out.println("------index-----");
        return "index";
    }
}
