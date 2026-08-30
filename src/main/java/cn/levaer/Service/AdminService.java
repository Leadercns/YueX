package cn.levaer.Service;

public interface AdminService {
    String login(String username, String password);

    String token(String username);

    String register(String username, String password,String code);
}
