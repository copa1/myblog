package com.copa.controller.login;

import com.copa.model.User;
import com.copa.service.UserService;
import com.copa.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制
 */
@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @ResponseBody
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("phone") String phone,
                                 @RequestParam("newPassword") String newPassword,
                                 HttpServletRequest request){

        User user = userService.findUserByPhone(phone);
        if(user == null){
            return "2";
        }
        MD5Util md5Util = new MD5Util();
        String MD5Password = md5Util.encode(newPassword);
        userService.updatePasswordByPhone(phone, MD5Password);

        return "1";
    }
}
