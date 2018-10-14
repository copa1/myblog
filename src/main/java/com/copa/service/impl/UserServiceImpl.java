package com.copa.service.impl;

import com.copa.constant.RoleConstant;
import com.copa.mapper.UserMapper;
import com.copa.model.User;
import com.copa.service.UserService;
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
