package com.copa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *  所有页面跳转
 */
@Controller
public class BackController {

    //打开登录页面
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    //打开注册页面
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    //打开个人中心页面
    @GetMapping("/user")
    public String user(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "user";
    }

    //去登录页
    @GetMapping("/toLogin")
    public @ResponseBody void toLogin(HttpServletRequest request){
        //保存跳转页面的url
        request.getSession().setAttribute("lastUrl", request.getHeader("Referer"));
    }

    //去编辑博客页面
    @GetMapping("/editor")
    public String editor(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        String id = request.getParameter("id");
        if(!"".equals(id)){
            request.getSession().setAttribute("id", id);
        }
        return "editor";
    }
}
