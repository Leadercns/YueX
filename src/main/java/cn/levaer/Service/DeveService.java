package cn.levaer.Service;

import jakarta.servlet.http.HttpServletRequest;

public interface DeveService {
    String Login(String username, String password, HttpServletRequest request,String Security_answer);

    String Register(String username, String password, String answer);

    String Reset(String username, String password);

    String getdeveid(String username, String password);
}
