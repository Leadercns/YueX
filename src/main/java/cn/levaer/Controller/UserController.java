package cn.levaer.Controller;


import cn.levaer.Service.UserService;
import cn.levaer.Tool.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api")
public class UserController {


    //注入服务层
    @Autowired
    private UserService userService;


    /**
     * 下属用户登录
     * @param id 开发者ID
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @PostMapping("/login")
    public Result login(String id,String username, String password) {

        String msg = userService.login(id,username,password);
        if (!msg.equals("登录成功")){
            return Result.error(msg);
        }

        return Result.success(msg,null);
    }

    /**
     * 下属用户注册
     * @param id 开发者ID
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @PostMapping("/register")
    public Result register(String id,String username, String password) {
        String msg = userService.register(id,username,password);
        if (!msg.equals("注册成功")){
            return Result.error(msg);
        }
        return Result.success(msg,null);
    }

    /**
     * 获取所有用户列表
     * @param id 开发者ID
     * @return
     */
    @GetMapping("/getallUser")
    public Result getUserInfo(String id) {
        // 调用服务层方法获取用户列表
        List<Map<String,Object>> userList = userService.getAllUser(id);
        if (userList == null) return Result.error("获取用户列表失败");

        return Result.success("获取用户列表成功",userList);
    }

    /**
     * 获取指定用户信息
     * @param id 开发者ID
     * @param username 用户名
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(String id, String username) {
        // 调用服务层方法获取用户信息
        Map<String,Object> userInfo = userService.getUserInfo(id,username);
        if (userInfo == null) return Result.error("获取用户信息失败");

        return Result.success("获取用户信息成功",userInfo);
    }

    /**
     * 封禁用户
     * @param id 开发者ID
     * @param username 用户名
     * @return
     */
    @GetMapping("/banUser")
    public Result BanUser(String id, String username) {
        // 调用服务层方法封禁用户
        String msg = userService.BanUser(id,username);
        if (!msg.equals("封禁成功")) return Result.error(msg);

        return Result.success(msg,null);
    }

    /**
     * 解封用户
     * @param id 开发者ID
     * @param username 用户名
     * @return
     */
    @GetMapping("/unbanUser")
    public Result UnbanUser(String id, String username) {
        // 调用服务层方法解封用户
        String msg = userService.UnbansUer(id,username);
        if (!msg.equals("解封成功")) return Result.error(msg);

        return Result.success(msg,null);
    }

    /**
     * 用户签到
     * @param id 开发者ID
     * @param username 用户名
     * @return
     */
    public Result checkIn(String id, String username) {
        // 调用服务层方法用户签到
        String msg = userService.checkIn(id,username);
        if (!msg.equals("签到成功")) return Result.error(msg);

        return Result.success(msg,null);
    }

}
