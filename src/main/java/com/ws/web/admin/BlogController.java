package com.ws.web.admin;

import com.ws.pojo.Blog;
import com.ws.pojo.User;
import com.ws.service.BlogService;
import com.ws.service.TagService;
import com.ws.service.TypeService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


/**
 * @author Syen
 * @date 2019/10/30 0030-下午 19:28
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST ="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blogQuery,
                        Model model){
        model.addAttribute("types",typeService.ListType());
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blogQuery,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.ListType());
        model.addAttribute("tags",tagService.ListTag());
        model.addAttribute("blog",new Blog());

        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession httpSession){
        blog.setUser( (User) httpSession.getAttribute("user"));

        blog.setType(typeService.getType(blog.getType().getId()));

        blog.setTags(tagService.ListTag(blog.getTagIds()));

       Blog blog1 = blogService.saveBlog(blog);
       if( blog1 == null){
           attributes.addFlashAttribute("message","操作失败");
       }else {
           attributes.addFlashAttribute("message","操作成功");
       }
       return REDIRECT_LIST;
    }

}
