package com.copa.service.impl;

import com.copa.mapper.UserMapper;
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

    @Override
    public void updateRecentlyLanded(String username, String recentlyLanded) {
            String phone = userMapper.findPhoneByUsername(username);
            userMapper.updateRecentlyLanded(phone, recentlyLanded);
    }
}
