package com.copa.mapper;

import com.copa.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 *  user表SQL语句
 */
@Mapper
@Repository
public interface UserMapper {


    //通过用户名来查找电话号码
    @Select("select phone from user where username=#{username}")
    String findPhoneByUsername(@Param("username") String username);

    //通过电话来修改该用户的最近登录时间
    @Update("update user set recentlyLanded=#{recentlyLanded} where phone=#{phone}")
    void updateRecentlyLanded(@Param("phone") String phone, @Param("recentlyLanded") String recentlyLanded);

    //通过电话查找相关用户信息
    @Select("select * from user where phone=#{phone}")
    User findUserByPhone(@Param("phone") String phone);

    //增加用户表
    @Insert("insert into user(phone,username,password,gender,avatarImgUrl) values(#{phone},#{username},#{password},#{gender},#{avatarImgUrl})")
    void insert(User user);

    //通过电话查找用户id
    @Select("select id from user where phone=#{phone}")
    int findUserIdByPhone(@Param("phone") String phone);

    //增加用户-权限表
    @Insert("insert into user_role(User_id, Role_id) values (#{userId}, #{roleId})")
    void insertRole(@Param("userId") int userId, @Param("roleId") int roleId);

    //通过用户名查找用户相关信息
    @Select("select * from user where username=#{username}")
    User findUsernameByUsername(@Param("username") String username);

    //通过用户名来查找用户相关信息
    @Select("select * from user where username=#{username}")
    User getUserPersonalInfoByUsername(@Param("username") String username);

    //通过用户名来查找该用户id
    @Select("select id from user where username=#{username}")
    int findIdByUsername(@Param("username") String username);

    //通过id（用户名）来查该用户的头像
    @Select("select avatarImgUrl from user where id=#{id}")
    String getHeadPortraitUrl(@Param("id") int id);

    //通过id来修改对应用户的头像
    @Update("update user set avatarImgUrl=#{avatarImgUrl} where id=#{id}")
    void updateAvatarImgUrlById(@Param("avatarImgUrl") String avatarImgUrl, @Param("id") int id);

    //通过用户名来保存个人信息
    @Update("update user set username=#{user.username},gender=#{user.gender},trueName=#{user.trueName},birthday=#{user.birthday},email=#{user.email},personalBrief=#{user.personalBrief} where username=#{username}")
    void savePersonalDate(@Param("user") User user, @Param("username") String username);
}
