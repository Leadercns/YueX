package cn.levaer.Mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface DeveMapper {

    @Select("SELECT COUNT(*) FROM developer WHERE username = #{username}")
    Integer checkDeve(String username);

    @Select("SELECT COUNT(*) FROM developer WHERE username = #{username} AND password = #{password}")
    Integer checkPassword(@Param("username") String username, @Param("password") String password);

    @Update("UPDATE developer SET login_ip = #{loginIP} WHERE username = #{username}")
    Integer updateLoginIP(@Param("username") String username, @Param("loginIP") String loginIP);

    @Insert("INSERT INTO developer (username, password, Security_answer, id) VALUES (#{username}, #{password}, #{answer}, #{userid})")
    Integer register(@Param("username") String username, @Param("password") String password,
                     @Param("answer") String answer, @Param("userid") String userid);

    @Select("SELECT State FROM developer WHERE username = #{username}")
    Integer checkBan(String username);

    // 修正：返回 COUNT 更安全
    @Select("SELECT COUNT(*) FROM developer WHERE username = #{username} AND Security_answer = #{securityAnswer}")
    Integer checkSecurity_answer(@Param("username") String username, @Param("securityAnswer") String securityAnswer);

    @Select("SELECT login_ip FROM developer WHERE username = #{username}")
    String getIP(String username);

    //重置id
    @Update("UPDATE developer SET id = #{userid} WHERE username = #{username}")
    Integer reset(String username, String password, String userid);
}