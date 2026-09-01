package cn.levaer.Mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {


    //检查开发者ID是否存在
    @Select("SELECT COUNT(*) FROM developer WHERE id = #{id}")
    int checkId(String id);


    //检查开发者的状态，0正常，1封禁
    @Select("SELECT State FROM developer WHERE id = #{id}")
    int checkStatus(String id);


    //检查用户名是否存在
    @Select("SELECT COUNT(*) FROM users WHERE deveid = #{id} AND username = #{username}")
    boolean checkUsername(String id, String username);

    //检查用户密码是否正确
    @Select("SELECT COUNT(*) FROM users WHERE deveid = #{id} AND username = #{username} AND password = #{password}")
    boolean checkPassword(String id, String username, String password);

    //检查用户状态 0正常 1封禁
    @Select("SELECT State FROM users WHERE deveid = #{id} AND username = #{username}")
    Integer checkUserStatus(String id, String username);


    //注册下属用户
    @Insert("INSERT INTO users (deveid, username, password) VALUES (#{id}, #{username}, #{password})")
    Integer register(String id, String username, String password);


    //获取所有用户
    @Select("SELECT username,Points,checktime,State FROM users WHERE deveid = #{id}")
    List<Map<String, Object>> getAllUser(String id);


    //获取用户信息
    @Select("SELECT username,Points,checktime,State FROM users WHERE deveid = #{id} AND username = #{username}")
    Map<String, Object> getUserInfo(String id, String username);

    //封禁用户
    @Update("UPDATE users SET State = 1 WHERE deveid = #{id} AND username = #{username}")
    Integer BanUser(String id, String username);

    //解封用户
    @Update("UPDATE users SET State = 0 WHERE deveid = #{id} AND username = #{username}")
    Integer UnbansUer(String id, String username);

    //获取用户签到时间
    @Select("SELECT checktime FROM users WHERE deveid = #{id} AND username = #{username}")
    String getCheckTime(String id, String username);


    //获取用户积分
    @Select("SELECT Points FROM users WHERE deveid = #{id} AND username = #{username}")
    int getUserPoints(String id, String username);

    //用户签到
    @Update("UPDATE users SET checktime = #{currentTimeStr}, Points = #{userPoints} WHERE deveid = #{id} AND username = #{username}")
    Integer checkIn(String id, String username, String currentTimeStr, int userPoints);
}
