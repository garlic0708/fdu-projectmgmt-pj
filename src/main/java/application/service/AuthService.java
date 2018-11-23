package application.service;

import application.entity.User;
import application.exception.RegisterException;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
public interface AuthService {
    void register(User userToAdd) throws RegisterException;
    String login(String email, String password);
    String refresh(String oldToken);
}
