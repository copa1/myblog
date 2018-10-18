package com.copa.controller;

import com.copa.service.UserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SuppressWarnings("all")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserService userService;

    /**
     * 获得个人资料
     */
    @GetMapping("/getUserPersonalInfo")
    public JSONObject getUserPersonalInfo(@AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        return userService.getUserPersonalInfoByUsername(username);
    }

    /**
     * 获得头像
     */
    @GetMapping("/getHeadPortraitUrl")
    public JSONObject getHeadPortraitUrl(@AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        return userService.getHeadPortraitUrl(userService.findIdByUsername(username));
    }
}
