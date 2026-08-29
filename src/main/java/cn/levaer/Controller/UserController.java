package cn.levaer.Controller;


import cn.levaer.Service.UserService;
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





}
