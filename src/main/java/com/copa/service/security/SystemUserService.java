package com.copa.service.security;

import com.copa.model.User;
import com.copa.repository.mybatis.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "con.copa.service.SystemUserService")
public class SystemUserService {

    @Autowired
    private UserRepository userRepository;

    public User findByPhone(String phone){
        return userRepository.findByPhone(phone);
    }

}
