package cn.levaer.Mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {

    //检查管理员账号是否存在
    @Select("select count(*) from admins where username = #{username}")
    Integer isExistence(String username);

    //检查密码是否正确
    @Select("select count(*) from admins where username =#{username} and password =#{password}")
    Integer ispasswordCorrect(String username, String password);


    //保存token
    @Insert("insert into admins (username, token, tokentime) values (#{username}, #{token}, #{currentTime})")
    void saveToken(String username, String token, String currentTime);


    //检查管理员账号是否被禁用
    @Select("select State from admins where username = #{username}")
    Integer isDisabled(String username);


    //检查邀请码是否存在
    @Select("select count(*) from admins where ICode = #{code}")
    Integer isCodeExistence(String code);


    @Insert("insert into admins (ICode) values (#{code})")
    void saveCode(String code);


    //检查邀请码是否被使用
    @Select("select ICodeS from admins where ICode = #{code}")
    Integer isCodeUsed(String code);

    //使用邀请码
    @Update("update admins set ICodeS = 1 where ICode = #{code}")
    Integer useCode(String code);


    @Insert("insert into admins (username, password, ICode,adminid) values (#{username}, #{password}, #{scode},#{adminID})")
    Integer register(String username, String password, String scode,String adminID);


    @Update("update admins set ICodeS = 1 where ICode = #{code}")
    void usecodes(String code);


    @Select("select count(*) from admins where ICode = #{code} and username ='admin' ")
    Integer isCodeFromAdmin(String code);

    @Update("update admins set State = 1 where username = 'admin'")
    void disableAdmin();

    @Select("select token from admins where username = #{username}")
    String gettoken(String username);


    @Select("select tokentime from admins where username = #{username}")
    String gettokentime(String username);


    @Update("update admins set token = #{token}, tokentime = #{time} where username = #{username}")
    void updateToken(String username, String token, String time);
}
