package application.service;

import application.entity.User;
import application.exception.JoinEventException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
public interface UserService {
    List<User> getAllUsers();

    Optional<User> getByEmail(String email);

    void joinEvent(int uid, int eid) throws JoinEventException;
}
