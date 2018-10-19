package com.copa.controller;

import com.copa.model.User;
import com.copa.service.UserService;
import com.copa.utils.FileUtil;
import com.copa.utils.TimeUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;


/**
 * 个人中心Controller
 */
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

    /**
     * 上传头像
     */
    @PostMapping("/uploadHead")
    public JSONObject uploadHead(HttpServletRequest request,
                                 @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            logger.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        JSONObject jsonObject = new JSONObject();
        String img = request.getParameter("img");
        //获得上传文件的后缀名
        int index = img.indexOf(";base64,");
        String strFileExtendName = "." + img.substring(11,index);
        img = img.substring(index + 8);

        try {
            FileUtil fileUtil = new FileUtil();
            String filePath = this.getClass().getResource("/").getPath().substring(1) + "userImg/";
            TimeUtil timeUtil = new TimeUtil();
            File file = fileUtil.base64ToFile(filePath, img, timeUtil.getLongTime() + strFileExtendName);
            //String url = fileUtil.uploadFile(file, "user/avatar/" + username);
            String url=file+"";
            int userId = userService.findIdByUsername(username);
            userService.updateAvatarImgUrlById(url, userId);
            jsonObject = userService.getHeadPortraitUrl(userId);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("更改头像失败",e.getMessage(),e);
            return jsonObject;
        }
        return jsonObject;
    }

    /**
     * 保存个人资料
     */
    @PostMapping("/savePersonalDate")
    public JSONObject savePersonalDate(User user, @AuthenticationPrincipal Principal principal){

        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            logger.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        return userService.savePersonalDate(user, username);
    }
}
