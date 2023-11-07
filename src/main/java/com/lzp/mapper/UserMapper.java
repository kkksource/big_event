package com.lzp.mapper;

import com.lzp.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    //根据用户名称查找用户
    @Select("select * from user where username = #{username}")
    User findByUserName(String username);

    //添加用户
    @Insert("insert into user(username,password,create_time,update_time) " +
            "values (#{username},#{password},now(),now())")
    void add(String username, String password);

    //跟新用户信息
    @Update("update user set nickname = #{nickname},email = #{email},update_time = #{updateTime}")
    void update(User user);

    //跟新用户头像
    @Update("update user set user_pic=#{avatarUrl},update_time = now() where id = #{id}")
    void updateAvatar(String avatarUrl,Integer id);

    //更新密码
    @Update("update user set password = #{password},update_time = now() where id = #{id}")
    void updatePwd(String password, Integer id);
}
