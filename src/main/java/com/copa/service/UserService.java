package com.copa.service;

import com.copa.model.User;

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
}
