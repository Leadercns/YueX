package cn.levaer.Service.Impl;

import cn.levaer.Mapper.AdminMapper;
import cn.levaer.Service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    //管理员登录
    @Override
    public String login(String username, String password) {
        Integer ise = isE(username, password);
        if (ise == 1) return "管理员账号或密码不能为空";

        //检查管理员账号是否存在
        Integer isExistence = adminMapper.isExistence(username);
        if (isExistence == 0) return "管理员账号不存在";

        //检查管理员密码是否正确
        Integer ispasswordCorrect = adminMapper.ispasswordCorrect(username,password);
        if (ispasswordCorrect == 0) return "管理员密码错误";


        //检查管理员账号是否被禁用
        Integer isDisabled = adminMapper.isDisabled(username);
        if (isDisabled == 1) return "管理员账号已封禁";


        // 5. 处理 token（24小时有效期）
        String token = adminMapper.gettoken(username);
        String tokenTimeStr = adminMapper.gettokentime(username);
        long currentTime = System.currentTimeMillis();
        String time = today();
        final long EXPIRE_TIME = 24 * 60 * 60 * 1000L; // 24小时毫秒数

        // 如果 token 不存在，或生成时间超过24小时，则重新生成
        if (token == null || tokenTimeStr == null) {
            // 首次登录或 token 丢失，生成新 token
            token = Generatetoken();
            adminMapper.updateToken(username, token, time);
        } else {
            try {
                long tokenTime = Long.parseLong(tokenTimeStr);
                if (currentTime - tokenTime > EXPIRE_TIME) {
                    // token 过期，重新生成并更新
                    token = Generatetoken();
                    adminMapper.updateToken(username, token, time);
                }
                // 否则 token 有效，复用
            } catch (NumberFormatException e) {
                // 如果时间格式异常，也重新生成（容错处理）
                token = Generatetoken();
                adminMapper.updateToken(username, token, time);
            }
        }



        return "登录成功";

    }

    //管理员注册
    @Override
    public String register(String username, String password,String code) {

        Integer ise = isE(username, password);
        if (ise == 1) return "管理员账号或密码不能为空";

        if (code.isEmpty()) return "邀请码不能为空";

        //检查管理员账号是否已存在
        Integer isExistence = adminMapper.isExistence(username);
        if (isExistence == 1) return "管理员账号已存在";

        //校验邀请码是否存在和是否已经被使用了
        //先校验邀请码是否存在
        Integer isCodeExistence = adminMapper.isCodeExistence(code);
        if (isCodeExistence == 0) return "邀请码不存在";

        //再校验邀请码是否已经被使用了
        Integer isCodeUsed = adminMapper.isCodeUsed(code);
        if (isCodeUsed == 1) return "邀请码已经使用过了";

        Integer isCodeUsed2 = adminMapper.useCode(code);
        if (isCodeUsed2 == 0) return "注册失败";

        //使用邀请码后，把邀请码使用状态改了
        adminMapper.usecodes(code);

        //查看邀请码是不是来自amdin的如果是直接封禁admin
        Integer isCodeFromAdmin = adminMapper.isCodeFromAdmin(code);
        if (isCodeFromAdmin == 1) adminMapper.disableAdmin();

        //生成邀请码长度10
        String scode = GenerateCode();
        //生成amdinID
        String adminID = GenerateAdminID();

        //注册管理员
        Integer isRegistered = adminMapper.register(username, password,scode,adminID);
        if (isRegistered == 0) return "注册失败";

        return "注册成功";
    }

    //生成token
    @Override
    public String token(String username) {

        //创建token
        String token = Generatetoken();
        //获取当前时间
        String currentTime = today();
        //将token和时间保存到数据库
        adminMapper.updateToken(username, token, currentTime);

        return token;
    }


    //管理员封禁开发者账号
    @Override
    public String banuser(String token, String username) {
        //检查参数问题
        if (token.isEmpty() || username.isEmpty())
            return "参数不能为空";

        //检查token是否存在
        Integer isTokenExistence = adminMapper.isTokenExistence(token);
        if (isTokenExistence == 0) return "token不存在";

        //检查上传的开发者账号是否存在
        Integer isExistence = adminMapper.isuserExistence(username);
        if (isExistence == 0) return "开发者账号不存在";

        //检查上传的开发者账号是否被封禁
        Integer isDisabled = adminMapper.isdeveDisabled(username);
        if (isDisabled == 1) return "开发者账号已封禁";

        //检查token是否有效，24h判断
        //获取token时间
        String tokenTimeStr = adminMapper.gettokentimeByToken(token);
        log.info("token时间文本数据库: " + tokenTimeStr);

        //获取当前时间戳,并把tokentime转成时间戳
        long currentTime = System.currentTimeMillis();
        log.info("当前时间戳: " + currentTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(tokenTimeStr, formatter);
        long tokenTime = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        log.info("转换后的时间戳: " + tokenTime);

        if (currentTime - tokenTime > 24 * 60 * 60 * 1000) return "token已过期";

        //封禁开发者账号
        Integer isBanned = adminMapper.banuser(username);
        if (isBanned == 0) return "封禁失败";
        return "封禁成功";

    }


    //管理员解封开发者账号
    @Override
    public String unseal(String token, String username) {
        //检查参数问题
        if (token.isEmpty() || username.isEmpty())
            return "参数不能为空";

        //检查token是否存在
        Integer isTokenExistence = adminMapper.isTokenExistence(token);
        if (isTokenExistence == 0) return "token不存在";

        //检查上传的开发者账号是否存在
        Integer isExistence = adminMapper.isuserExistence(username);
        if (isExistence == 0) return "开发者账号不存在";

        //检查上传的开发者账号是否被封禁
        Integer isDisabled = adminMapper.isdeveDisabled(username);
        if (isDisabled == 0) return "开发者账号状态正常";

        ///检查token是否有效，24h判断
        //获取token时间
        String tokenTimeStr = adminMapper.gettokentimeByToken(token);
        //获取当前时间戳,并把tokentime转成时间戳
        long currentTime = System.currentTimeMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(tokenTimeStr, formatter);
        long tokenTime = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        if (currentTime - tokenTime > 24 * 60 * 60 * 1000) return "token已过期";
        //解封开发者账号
        Integer isUnsealed = adminMapper.unseal(username);
        if (isUnsealed == 0) return "解封失败";
        return "解封成功";

    }

    private Integer isE(String username, String password){

        if (username.isEmpty() || password.isEmpty())
            //表示username和password为空
            return 1;
        return 2;
    }


    private String Generatetoken(){

        //随机生成token
        String zf = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String token = "";
        for (int i = 0; i < 16; i++) {
            int index = (int) (Math.random() * zf.length());
            token += zf.charAt(index);
        }

        return token;
    }

    private String GenerateCode(){
        String zf = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String code = "";
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * zf.length());
            code += zf.charAt(index);
        }
        return code;
    }

    private String GenerateAdminID(){
        //长度为15
        String zf = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String adminID = "";
        for (int i = 0; i < 15; i++) {
            int index = (int) (Math.random() * zf.length());
            adminID += zf.charAt(index);
        }
        return adminID;
    }

    private String today() {
        // 返回当前时间 年月日 hh:mm:ss
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
