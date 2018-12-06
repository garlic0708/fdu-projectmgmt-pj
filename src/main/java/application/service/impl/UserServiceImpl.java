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

import java.util.LinkedList;
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
    public void joinEvent(int uid, int eid) throws JoinEventException {
        Event event = eventRepository.findByEId(eid);
        User user = userRepository.findByUId(uid);
        if (user == null)
            throw new JoinEventException("No such user");
        if (event == null)
            throw new JoinEventException("No such event");
        if (!event.getEventState().equals("notStarted"))
            throw new JoinEventException("You can not join the event at now");
        if (joinEventRepository.findByUIdAndEId(uid, eid) != null)
            throw new JoinEventException("You have joined the event");
        if (joinEventRepository.getParticipantsByEId(eid).size() >= event.getUpperLimit())
            throw new JoinEventException("Full");
        if (event.getLimited() && event.getCreditLimit() > user.getVredict())
            throw new JoinEventException("You don't have enough credit");
        JoinEvent joinEvent = new JoinEvent();
        joinEvent.setuId(uid);
        joinEvent.seteId(eid);
        joinEvent.setJeState(JoinEvent.PARTICIPATED);
        joinEventRepository.save(joinEvent);
    }

    @Override
    public List<UserCheckIn> getUserCheckIn(int eid) {
        List<JoinEvent> joinEvents = joinEventRepository.findByEId(eid);
        List<UserCheckIn> userCheckIns = new LinkedList<>();
        for (int i = 0; i < joinEvents.size(); i++) {
            JoinEvent joinEvent = joinEvents.get(i);
            int uid = joinEvent.getuId();
            String jeState = joinEvent.getJeState();
            UserCheckIn userCheckIn = new UserCheckIn();
            if (jeState.equals("participated"))
                userCheckIn.setType(false);
            else if (jeState.equals("check"))
                userCheckIn.setType(true);
            else if (jeState.equals("initiator"))
                continue;
            userCheckIn.setUid(uid);
            User user = userRepository.findByUId(uid);
            userCheckIn.setName(user.getNickname());
            userCheckIns.add(userCheckIn);
        }

        return userCheckIns;
    }
}
