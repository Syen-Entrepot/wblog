package com.ws.web;

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

/**
 * @author Syen
 * @date 2019/10/28 0028-下午 12:36
 */
@Controller
public class indexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
      model.addAttribute("page",blogService.listBlog(pageable));
      model.addAttribute("types",typeService.listTypeTop(6));
      model.addAttribute("tags",tagService.listTagTop(10));
        return "index";
    }

    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }
}
