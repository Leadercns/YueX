package cn.levaer.Controller;

import cn.levaer.Service.DeveService;
import cn.levaer.Tool.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Result Login(String username, String password,HttpServletRequest request,
                        @RequestParam(required = false) String Security_answer) {
        //调用服务层
        String msg = deveService.Login(username, password, request,Security_answer);
        if (!msg.equals("登录成功")){
            return Result.error(msg);
        }

        return Result.success("登录成功",null);
    }



    /**
     * 注册
     * @param username
     * @param password
     * @param answer
     * @return
     */
    @PostMapping("/Register")
    public Result Register(String username, String password,String answer) {
        //调用服务层
        String msg = deveService.Register(username, password,answer);
        if (!msg.equals("注册成功")){
            return Result.error(msg);
        }
        return Result.success(msg,null);
    }


    /**
     * 重置ID
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/Reset")
    public Result Reset(String username, String password) {

        //调用服务层
        String msg = deveService.Reset(username, password);
        if (!msg.equals("重置成功")){
            return Result.error(msg);
        }
        String s = "！请重新对接";
        return Result.success(msg + s,null);
    }

}
