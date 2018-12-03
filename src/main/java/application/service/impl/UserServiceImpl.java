package application.service.impl;

import application.entity.JoinEvent;
import application.entity.User;
import application.repository.JoinEventRepository;
import application.repository.UserRepository;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private JoinEventRepository joinEventRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           JoinEventRepository joinEventRepository) {
        this.userRepository = userRepository;
        this.joinEventRepository = joinEventRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.allStudents();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void joinEvent(int uid, int eid) {
        JoinEvent joinEvent = new JoinEvent();
        joinEvent.setuId(uid);
        joinEvent.seteId(eid);
        joinEvent.setJeState("participated");
        joinEventRepository.save(joinEvent);
    }
}
