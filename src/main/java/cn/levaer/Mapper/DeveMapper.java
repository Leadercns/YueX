package cn.levaer.Mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface DeveMapper {

    //检查开发者是否存在
    @Select("SELECT COUNT(*) FROM developer WHERE username = #{username}")
    Integer checkDeve(String username);


    //检查开发者密码是否错误 1正确 0错误
    @Select("SELECT COUNT(*) FROM developer WHERE username = #{username} AND password = #{password}")
    Integer checkPassword(String username, String password);


    //更新登录IP
    @Update("UPDATE developer SET login_ip = #{loginIP} WHERE username = #{username}")
    Integer updateLoginIP(String username,String loginIP);

    //注册开发者
    @Insert("INSERT INTO developer (username, password, Security_answer, id) VALUES (#{username}, #{password}, #{answer}, #{userid})")
    Integer register(@Param("username") String username, @Param("password") String password, @Param("answer") String answer,@Param("userid")     String userid);
}
