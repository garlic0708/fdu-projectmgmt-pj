package application.service;

import application.entity.User;
import application.entity.userSecurity.UpdatePasswordForm;
import application.entity.userSecurity.VerificationToken;
import application.exception.RegisterException;
import application.exception.UpdatePasswordException;
import application.exception.VerificationException;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
public interface AuthService {
    VerificationToken register(User userToAdd) throws RegisterException;
    void registrationConfirm(String token) throws VerificationException;
    void updatePassword(UpdatePasswordForm upf, boolean isReset)  throws UpdatePasswordException;
    String login(String email, String password);
    String refresh(String oldToken);

    void reset(String email);
}
