package application.service;

import application.entity.User;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
public interface AuthService {
    User register(User userToAdd);
    String login(String email, String password);
    String refresh(String oldToken);
}
