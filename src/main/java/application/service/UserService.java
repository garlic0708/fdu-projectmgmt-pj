package application.service;

import application.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
public interface UserService {
    List<User> getAllUsers();
}
