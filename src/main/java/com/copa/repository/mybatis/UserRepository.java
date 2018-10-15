package com.copa.repository.mybatis;

import com.copa.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * xml配置User表操作
 */
@Repository
public interface UserRepository {

    /**
     *  通过手机号查找用户
     * @param phone 手机号
     * @return 用户
     */
    User findByPhone(String phone);

}
