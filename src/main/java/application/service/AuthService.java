package application.service;

import application.entity.User;
import application.entity.userSecurity.UpdatePasswordForm;
import application.exception.RegisterException;
import application.exception.UpdatePasswordException;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
public interface AuthService {
    void register(User userToAdd) throws RegisterException;
    void updatePassword(UpdatePasswordForm upf)  throws UpdatePasswordException;
    String login(String email, String password);
    String refresh(String oldToken);
}
