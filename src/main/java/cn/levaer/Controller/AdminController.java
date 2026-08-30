package cn.levaer.Controller;


import cn.levaer.Service.AdminService;
import cn.levaer.Tool.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;


    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/Login")
    public Result Login(String username,String password){

        //调用服务层
        String msg = adminService.login(username,password);
        if (!msg.equals("登录成功")){
            return Result.error(msg);
        }

        String token = adminService.token(username);
        return Result.success(msg,token);

    }

    /**
     * 管理员注册
     * @param username
     * @param password
     * @param code
     * @return
     */
    @PostMapping("/Register")
    public Result Register(String username,String password,String code){
        String msg = adminService.register(username,password,code);
        if (!msg.equals("注册成功")){
            return Result.error(msg);
        }
        return Result.success(msg, null);
    }








}
