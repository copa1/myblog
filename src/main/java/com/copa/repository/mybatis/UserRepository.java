package com.copa.repository.mybatis;

import com.copa.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface UserRepository {

    @Select("select * from user where phone=#{phone}")
    User findByPhone(String phone);
}
