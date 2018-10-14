package com.copa.service;

import com.copa.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 *  user业务操作
 */
public interface UserService {

    /**
     * 更新最近登录时间
     * @param username 用户名
     * @param recentlyLanded 最近登录时间
     */
    void updateRecentlyLanded(String username, String recentlyLanded);

    /**
     * 注册用户
     * @param user 用户
     * @return "1"--用户存在，插入失败             "2"--用户不存在，插入成功
     */
    @Transactional
    String insert(User user);

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return true--存在  false--不存在
     */
    boolean usernameIsExit(String username);
}
