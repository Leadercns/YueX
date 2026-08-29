package cn.levaer.Controller;

import cn.levaer.Service.DeveService;
import cn.levaer.Tool.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeveController {

    @Autowired
    private DeveService deveService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/Login")
    public Result Login(String username, String password) {
        //调用服务层
        String msg = deveService.Login(username, password);
        if (!msg.equals("登录成功")){
            return Result.success(msg,null);
        }

        return Result.success("登录成功",null);
    }



    @PostMapping("/Register")
    public Result Register(String username, String password,String answer) {
        //调用服务层
        String msg = deveService.Register(username, password,answer);
        if (!msg.equals("注册成功")){
            return Result.error(msg);
        }
        return Result.success(msg,null);
    }

}
