package cn.levaer.Service;

import jakarta.servlet.http.HttpServletRequest;

public interface DeveService {
    String Login(String username, String password, HttpServletRequest request);

    String Register(String username, String password, String answer);
}
