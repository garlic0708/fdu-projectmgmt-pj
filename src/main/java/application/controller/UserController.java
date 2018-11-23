package application.controller;

import application.entity.User;
import application.repository.UserRepository;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return (userService.getAllUsers());
    }

    @RequestMapping(value = "/users2", method = RequestMethod.GET)
    public List<User> getUsers2() {
        return (userService.getAllUsers());
    }
}
