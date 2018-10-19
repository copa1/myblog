package com.copa.service.impl;

import com.copa.constant.RoleConstant;
import com.copa.mapper.UserMapper;
import com.copa.model.User;
import com.copa.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  user表接口具体业务逻辑
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    //更新最近登录时间
    @Override
    public void updateRecentlyLanded(String username, String recentlyLanded) {
            String phone = userMapper.findPhoneByUsername(username);
            userMapper.updateRecentlyLanded(phone, recentlyLanded);
    }

    @Override
    public String insert(User user) {
        if (userIsExit(user.getPhone())){
            return "1";
        }
        user.setAvatarImgUrl("http://www.qqtouxiang.com/d/file/tupian/mx/20170807/jiqwys341azf0.jpg");
        userMapper.insert(user);
        int userId=userMapper.findUserIdByPhone(user.getPhone());
        //System.out.println("aa"+userId);
        insertRole(userId, RoleConstant.ROLE_USER);
        return "2";
    }

    @Override
    public boolean usernameIsExit(String username) {
        User user = userMapper.findUsernameByUsername(username);
        return user != null;
    }

    @Override
    public JSONObject getUserPersonalInfoByUsername(String username) {
        User user = userMapper.getUserPersonalInfoByUsername(username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        JSONObject userJon = new JSONObject();
        userJon.put("phone",user.getPhone());
        userJon.put("username",user.getUsername());
        userJon.put("gender",user.getGender());
        userJon.put("trueName",user.getTrueName());
        userJon.put("birthday",user.getBirthday());
        userJon.put("email",user.getEmail());
        userJon.put("personalBrief",user.getPersonalBrief());
        userJon.put("avatarImgUrl",user.getAvatarImgUrl());
        jsonObject.put("result",userJon);
        return jsonObject;
    }

    @Override
    public int findIdByUsername(String username) {
        return userMapper.findIdByUsername(username);
    }

    @Override
    public JSONObject getHeadPortraitUrl(int id) {
        JSONObject jsonObject = new JSONObject();
        String avatarImgUrl = userMapper.getHeadPortraitUrl(id);
        if(!"".equals(avatarImgUrl) && avatarImgUrl != null){
            jsonObject.put("status",200);
            jsonObject.put("avatarImgUrl",avatarImgUrl);
        }else {
            jsonObject.put("status",404);
            jsonObject.put("avatarImgUrl","http://www.qqtouxiang.com/d/file/tupian/mx/20170807/jiqwys341azf0.jpg");
        }
        return jsonObject;
    }

    @Override
    public void updateAvatarImgUrlById(String avatarImgUrl, int userId) {
        userMapper.updateAvatarImgUrlById(avatarImgUrl, userId);
    }

    /**
     * 通过手机号判断用户是否存在
     * @param phone 手机号
     * @return true--存在  false--不存在
     */
    private boolean userIsExit(String phone){
        User user = userMapper.findUserByPhone(phone);
        return user != null;
    }

    /**
     * 增加用户权限
     * @param userId 用户id
     * @param roleId 权限id
     */
    private void insertRole(int userId, int roleId) {
        userMapper.insertRole(userId, roleId);
    }
}
