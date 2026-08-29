package cn.levaer.Service.Impl;

import cn.levaer.Mapper.DeveMapper;
import cn.levaer.Service.DeveService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeveServiceImpl implements DeveService{

    @Autowired
    private DeveMapper deveMapper;
    @Autowired
    private HttpServletRequest request;

    //登录
    @Override
    public String Login(String username, String password,HttpServletRequest request) {

        boolean checkcs = checkcs(username, password);
        if (checkcs == false) return "登录失败";
        if (password.length() < 6) return "密码长度不能小于6位";

        //检查开发者的账号是否存在
        Integer isDeve = deveMapper.checkDeve(username);
        if (isDeve == 0)
            return "账号不存在";

        //检查开发者的密码是否存在
        Integer isPassword = deveMapper.checkPassword(username, password);
        if (isPassword == 0)
            return "密码错误";

        //更新登录IP
        String loginIP = getClientIp(request);
        Integer updateLoginIP = deveMapper.updateLoginIP(username, loginIP);
        if (updateLoginIP == 0)
            return "更新登录IP失败";


        return "登录成功";
    }


    //注册
    @Override
    public String Register(String username, String password, String answer) {
        boolean checkcs = checkcs(username, password);
        if (checkcs == false) return "登录失败";
        if (password.length() < 6) return "密码长度不能小于6位";

        //检查开发者的账号是否存在
        Integer isDeve = deveMapper.checkDeve(username);
        if (isDeve == 1)
            return "账号已存在";

        //生成用户ID长度18位
        String userid = generateUserId();
        Integer register = deveMapper.register(username, password, answer,userid);
        if (register == 0)
            return "注册失败";


        return "注册成功";
    }

    private boolean checkcs(String username, String password) {

        if (username.isEmpty() || password.isEmpty()){
            return false;
        }

        return true;

    }


    // 提取 IP 的私有方法
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果通过多个代理，取第一个 IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }


    //生成用户ID，长度18位方法
    public String generateUserId() {

        String arr ="ABCDEFGHIJKLMOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 18; i++) {
            int index = (int) (Math.random() * arr.length());
            sb.append(arr.charAt(index));
        }
        return sb.toString();
    }

}
