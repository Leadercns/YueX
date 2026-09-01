package cn.levaer.Service;

import java.util.List;
import java.util.Map;

public interface UserService {
    String login(String id,String username, String password);

    String register(String id, String username, String password);

    List<Map<String, Object>> getAllUser(String id);

    Map<String, Object> getUserInfo(String id, String username);

    String BanUser(String id, String username);

    String UnbansUer(String id, String username);

    String checkIn(String id, String username);
}
