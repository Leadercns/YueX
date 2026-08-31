package cn.levaer.Controller;


import cn.levaer.Service.UserService;
import cn.levaer.Tool.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class UserController {


    //注入服务层
    @Autowired
    private UserService userService;


    //下属用户登录
    @RequestMapping("/login")
    public Result login(String username, String password) {

        String msg = userService.login(username,password);
        if (!msg.equals("登录成功")){
            return Result.error(msg);
        }

        return Result.success(msg,null);
    }





}
