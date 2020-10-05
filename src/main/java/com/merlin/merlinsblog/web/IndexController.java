package com.merlin.merlinsblog.web;


import com.merlin.merlinsblog.Exception.NotFoundBlogException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
//        @PathVariable Integer id,@PathVariable String name
//        int i = 9/0;
//        String blog = null;
//        if(blog == null){
//            throw new NotFoundBlogException("博客不存在");
//        }

        System.out.println("----------index----------");
//        System.out.println(id + ":" + name);
        return "blog";
    }
}
