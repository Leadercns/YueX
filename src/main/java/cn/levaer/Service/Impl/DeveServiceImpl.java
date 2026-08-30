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


    //登录
    @Override
    public String Login(String username, String password, HttpServletRequest request, String Security_answer) {
        // 判空（使用 trim 处理空格）
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return "用户名或密码不能为空";
        }
        if (password.length() < 6) return "密码长度不能小于6位";

        Integer isDeve = deveMapper.checkDeve(username);
        if (isDeve == 0) return "开发者账号不存在";

        Integer isPassword = deveMapper.checkPassword(username, password);
        if (isPassword == 0) return "密码错误";

        Integer isBan = deveMapper.checkBan(username);
        if (isBan == 1) return "开发者账号已封禁";

        String requestIP = getClientIp(request);
        String databaseIP = deveMapper.getIP(username);

        // IP 不一致且数据库有 IP（非首次登录）才验证安全问题
        if (databaseIP != null && !databaseIP.equals(requestIP)) {
            if (Security_answer == null || Security_answer.trim().isEmpty()) {
                return "安全问题不能为空";
            }
            Integer answerCheck = deveMapper.checkSecurity_answer(username, Security_answer);
            if (answerCheck == 0) {
                return "安全问题答案错误或不存在";
            }
        }

        // 更新 IP（使用 requestIP）
        Integer update = deveMapper.updateLoginIP(username, requestIP);
        if (update == 0) return "更新登录IP失败";

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
