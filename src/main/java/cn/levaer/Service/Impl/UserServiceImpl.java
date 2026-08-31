package cn.levaer.Service.Impl;

import cn.levaer.Mapper.UserMapper;
import cn.levaer.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;


    @Override
    public String login(String username, String password) {
        return "";
    }
}
