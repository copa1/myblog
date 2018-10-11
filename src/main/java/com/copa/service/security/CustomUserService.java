package com.copa.service.security;

import com.copa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService{

    @Autowired
    private SystemUserService systemUserService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //根据用户名从数据库查询对应记录
        User user=systemUserService.findByPhone(s);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在！");
        }

        System.out.println("username is : " + user.getUsername() + ", password is :" + user.getPassword());
        return user;
    }
}
