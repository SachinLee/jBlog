package cn.sachin.jaBlog.controller;

import cn.sachin.jaBlog.common.ServerResult;
import cn.sachin.jaBlog.pojo.Blog;
import cn.sachin.jaBlog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/p")
public class BlogController {

    @Autowired
    private BlogService blogService;

    public ModelAndView blogList() {
        ModelAndView view = new ModelAndView("");

        return view;
    }

    /**
     * 写博客
     * @param username
     * @return
     */
    @GetMapping(value = "/{username}/edit")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView editBlog(@PathVariable(name = "username") String username) {
        ModelAndView modelAndView = new ModelAndView("users/EditeArticle");

        return modelAndView;
    }

    @PostMapping(value = "/save")
    public ServerResult saveBlog(Blog blog) {
        blogService.saveBlog(blog);
        return ServerResult.createResultBySuccess();
    }

}
