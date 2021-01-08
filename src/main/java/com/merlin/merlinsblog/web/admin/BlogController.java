package com.merlin.merlinsblog.web.admin;


import com.merlin.merlinsblog.po.Blog;
import com.merlin.merlinsblog.po.User;
import com.merlin.merlinsblog.service.BlogService;
import com.merlin.merlinsblog.service.TypeService;
import com.merlin.merlinsblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {

//    private static final String INPUT = "admin/blogInput";
//    private static final String LIST = "admin/blogManager";
//    private static final String REDIRECT_LIST = "redirect:/admin/blogManager";


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

//    @Autowired
//    Private TagService tagService;

    @GetMapping("/blog")
//    @PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC)
//    指定分页大小，排序规则，排序顺序 等等~
    public String blog(@PageableDefault(size = 5,sort = {"updateTIme"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogManager";
    }

    @PostMapping("/blog/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTIme"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogManager :: bloglist";
    }

    @GetMapping("/blog/input")
    public String input(Model model){
//        model.addAttribute("types",typeService.listType());
        setType(model);
        model.addAttribute("blog",new Blog());
        return "admin/blogSubmit";
    }

    private void setType(Model model){
        model.addAttribute("types",typeService.listType());
    }

    @GetMapping("/blog/{id}/input")
    public String editinput(@PathVariable Long id, Model model){
//        model.addAttribute("types",typeService.listType());
        setType(model);
        model.addAttribute("blog",blogService.getBlog(id));
        return "admin/blogInput";
    }

    @PostMapping("/blog")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        Blog b = blogService.saveBlog(blog);
        if(b==null){
            redirectAttributes.addFlashAttribute("message","操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/blog";
    }



}
