package com.merlin.merlinsblog.web.admin;

import com.merlin.merlinsblog.po.User;
import com.merlin.merlinsblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/admin")//,method = {RequestMethod.POST}
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {

        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes){
        User user = userService.chechUser(username,password);
        if(user != null){
            System.out.println(user.toString());
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            //重定向只能使用以下的方式才能传递message
            redirectAttributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";

    }
}
