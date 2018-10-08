package com.copa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ArticleController {

    @RequestMapping(value = "/t",method = RequestMethod.GET)
    public String test(){
        return "index";
    }
}
