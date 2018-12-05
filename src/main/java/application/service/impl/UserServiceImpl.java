package application.service.impl;

import application.entity.Event;
import application.entity.JoinEvent;
import application.entity.User;
import application.entity.forms.UserCheckIn;
import application.exception.JoinEventException;
import application.repository.EventRepository;
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
    private EventRepository eventRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           JoinEventRepository joinEventRepository,
                           EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.joinEventRepository = joinEventRepository;
        this.eventRepository = eventRepository;
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
    public void joinEvent(int uid, int eid)  throws JoinEventException {
        Event event = eventRepository.findByEId(eid);
        if (event == null)
            throw new JoinEventException("No such event");
        if (!event.getEventState().equals("notStarted"))
            throw new JoinEventException("You can not join the event at now");
        if (joinEventRepository.findByUIdAndEId(uid, eid) != null)
            throw new JoinEventException("You have joined the event");
        if (joinEventRepository.getParticipantsByEId(eid).size() >= event.getUpperLimit())
            throw new JoinEventException("Full");
        JoinEvent joinEvent = new JoinEvent();
        joinEvent.setuId(uid);
        joinEvent.seteId(eid);
        joinEvent.setJeState(JoinEvent.PARTICIPATED);
        joinEventRepository.save(joinEvent);
    }

    @Override
    public List<UserCheckIn> getUserCheckIn(int eid) {
        //TODO
        return null;
    }
}
