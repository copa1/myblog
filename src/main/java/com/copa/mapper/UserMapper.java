package com.copa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 *  user表SQL语句
 */
@Mapper
@Repository
public interface UserMapper {

    @Select("select phone from user where username=#{username}")
    String findPhoneByUsername(@Param("username") String username);

    @Update("update user set recentlyLanded=#{recentlyLanded} where phone=#{phone}")
    void updateRecentlyLanded(@Param("phone") String phone, @Param("recentlyLanded") String recentlyLanded);

}
