package com.copa.service.security;

import com.copa.model.Role;
import com.copa.model.User;
import com.copa.repository.mybatis.UserRepository;
import com.copa.service.UserService;
import com.copa.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *  用户登录处理
 */

@Service
public class CustomUserService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        //根据用户名从数据库查询对应记录
        User user=userRepository.findByPhone(phone);
        if (user == null){
            return (UserDetails) new BadCredentialsException("用户不存在");
        }

        TimeUtil timeUtil = new TimeUtil();
        String recentlyLanded = timeUtil.getFormatDateForSix();
        userService.updateRecentlyLanded(user.getUsername(), recentlyLanded);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        //System.out.println(phone + "用户登录成功");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
