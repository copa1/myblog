package com.copa.controller.register;

import com.copa.model.Password;
import com.copa.model.User;
import com.copa.service.UserService;
import com.copa.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 注册Controller
 */
@Controller
public class RegisterControl {

    @Autowired
    UserService userService;

    /**
     *  提交注册表单，注册一个用户
     */
    @PostMapping("/register")
    @ResponseBody
    public String register(User user, HttpServletRequest request){

        //用户名存在，返回“3”
        if(userService.usernameIsExit(user.getUsername())){
            return "3";
        }
        Password password=new Password();
        password.setPassword(user.getPassword());
        password.setUsername(user.getUsername());
        //注册时对密码进行MD5加密
        MD5Util md5Util=new MD5Util();
        user.setPassword(md5Util.encode(user.getPassword()));
        return userService.insert(user);
    }
}
