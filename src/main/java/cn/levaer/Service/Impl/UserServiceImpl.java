package cn.levaer.Service.Impl;

import cn.levaer.Mapper.AdminMapper;
import cn.levaer.Mapper.UserMapper;
import cn.levaer.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    //下属用户登录
    @Override
    public String login(String id,String username, String password) {

        //检查参数
        if (id == null || username == null || password == null) {
            return "参数错误";
        }

        //检查开发者ID是否存在
        if (userMapper.checkId(id) == 0) {
            return "开发者ID不存在";
        }

        //检查开发者的状态，0正常，1封禁
        if (userMapper.checkStatus(id) == 1) {
            return "开发者已封禁";
        }

        //检查用户名是否正确
        if (!userMapper.checkUsername(id, username)) {
            return "用户名错误";
        }

        //检查用户密码是否正确
        if (!userMapper.checkPassword(id, username, password)) {
            return "用户密码错误";
        }

        //检查用户状态
        Integer userStatus = userMapper.checkUserStatus(id, username);
        if (userStatus == 1) return "用户已被封禁";

        return "登录成功";
    }


    //下属用户注册
    @Override
    public String register(String id, String username, String password) {
        //检查参数
        if (id == null || username == null || password == null) {
            return "参数错误";
        }

        //检查开发者ID是否存在
        if (userMapper.checkId(id) == 0) {
            return "开发者ID不存在";
        }

        //检查开发者的状态，0正常，1封禁
        if (userMapper.checkStatus(id) == 1) {
            return "开发者已封禁";
        }

        //检查用户名是否已存在
        if (userMapper.checkUsername(id, username)) {
            return "用户名已存在";
        }

        Integer registerResult = userMapper.register(id, username, password);
        if (registerResult == 0) return "注册失败";
        return "注册成功";

    }


    //获取所有用户
    @Override
    public List<Map<String, Object>> getAllUser(String id) {

        //参数检查
        if (id.isEmpty()) return null;

        //依靠ID查询开发者状态
        if (userMapper.checkStatus(id) == 1) {
            return null;
        }

        //获取所有用户
        List<Map<String, Object>> allUser = userMapper.getAllUser(id);
        if (allUser == null) return null;

        //把所有的状态改为中文
        for (Map<String, Object> user : allUser) {
            Integer status = (Integer) user.get("State");
            if (status == 0) {
                user.put("State", "正常");
            } else {
                user.put("State", "封禁");
            }
        }


        return allUser;
    }


    //获取用户信息
    @Override
    public Map<String, Object> getUserInfo(String id, String username) {
        //参数检查
        if (id.isEmpty() || username.isEmpty()) return null;

        //检查开发者ID是否存在
        if (userMapper.checkId(id) == 0) {
            return null;
        }

        //检查开发者状态
        if (userMapper.checkStatus(id) == 1) {
            return null;
        }

        //检查用户名是否正确
        if (!userMapper.checkUsername(id, username)) {
            return null;
        }

        Map<String,Object> userInfo = userMapper.getUserInfo(id, username);
        if (userInfo == null) return null;

        //把状态改为中文
        Integer status = (Integer) userInfo.get("State");
        if (status == 0) {
            userInfo.put("State", "正常");
        } else {
            userInfo.put("State", "封禁");
        }

        return userInfo;
    }


    //封禁用户
    @Override
    public String BanUser(String id, String username) {
        //参数检查
        if (id.isEmpty() || username.isEmpty()) return null;

        //检查开发者ID是否存在
        if (userMapper.checkId(id) == 0) {
            return null;
        }

        //检查开发者状态
        if (userMapper.checkStatus(id) == 1) {
            return null;
        }

        //检查用户名是否正确
        if (!userMapper.checkUsername(id, username)) {
            return null;
        }

        //封禁用户
        Integer banUserResult = userMapper.BanUser(id, username);
        if (banUserResult == 0) return "封禁失败";

        return "封禁成功";
    }


    //解封用户
    @Override
    public String UnbansUer(String id, String username) {
        //参数检查
        if (id.isEmpty() || username.isEmpty()) return null;

        //检查开发者ID是否存在
        if (userMapper.checkId(id) == 0) {
            return null;
        }

        //检查开发者状态
        if (userMapper.checkStatus(id) == 1) {
            return null;
        }

        //检查用户名是否正确
        if (!userMapper.checkUsername(id, username)) {
            return null;
        }

        //解封用户
        Integer unbanUserResult = userMapper.UnbansUer(id, username);
        if (unbanUserResult == 0) return "解封失败";

        return "解封成功";
    }


    //用户签到
    @Override
    public String checkIn(String id, String username) {
        //参数检查
        if (id.isEmpty() || username.isEmpty()) return null;

        //检查开发者ID是否存在
        if (userMapper.checkId(id) == 0) {
            return null;
        }

        //检查开发者状态
        if (userMapper.checkStatus(id) == 1) {
            return null;
        }

        //检查用户名是否正确
        if (!userMapper.checkUsername(id, username)) {
            return null;
        }

        //检查用户是否已签到24h制
        //获取用户签到时间
        String checkTime = userMapper.getCheckTime(id, username);
        //获取当前时间戳
        long currentTime = System.currentTimeMillis();
        //把checkTime转成时间戳
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //转成时间戳
        LocalDateTime dateTime = LocalDateTime.parse(checkTime, formatter);
        long checkTimeMillis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if (currentTime - checkTimeMillis < 24 * 60 * 60 * 1000){
            return "请勿重复签到";
        }
        //获取当前时间
        String currentTimeStr = LocalDateTime.now().format(formatter);
        //获取用户积分
        int userPoints = userMapper.getUserPoints(id, username);
        userPoints += 10;//签到加10积分
        //用户签到
        Integer checkInResult = userMapper.checkIn(id, username,currentTimeStr,userPoints);
        if (checkInResult == 0) return "签到失败";
        return "签到成功";

    }


}