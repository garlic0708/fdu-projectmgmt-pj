package application.service;

import application.SpringBootWebApplication;
import application.entity.Event;
import application.entity.JoinEvent;
import application.entity.User;
import application.entity.forms.UserCheckIn;
import application.repository.EventRepository;
import application.repository.JoinEventRepository;
import application.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {SpringBootWebApplication.class})
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JoinEventRepository joinEventRepository;
    @Autowired
    private EventRepository eventRepository;

    private JoinEvent joinEvent1, joinEvent2, joinEvent3;

    private Event event;
    private User u1, u2, u3;

    @Before
    public void setUp() {
        event = new Event();
        event = eventRepository.save(event);
        u1 = new User();
        u1.setNickname("gtx");
        u1 = userRepository.save(u1);

        u2 = new User();
        u2.setNickname("aa");
        u2 = userRepository.save(u2);

        u3 = new User();
        u3.setNickname("bb");
        u3 = userRepository.save(u3);
        int eid = event.geteId();

        joinEvent1 = new JoinEvent();
        joinEvent1.seteId(eid);
        joinEvent1.setuId(u1.getuId());
        joinEvent1.setJeState("initiator");
        joinEventRepository.save(joinEvent1);

        joinEvent2 = new JoinEvent();
        joinEvent2.seteId(eid);
        joinEvent2.setuId(u2.getuId());
        joinEvent2.setJeState("check");
        joinEventRepository.save(joinEvent2);

        joinEvent3 = new JoinEvent();
        joinEvent3.seteId(eid);
        joinEvent3.setuId(u3.getuId());
        joinEvent3.setJeState("participated");
        joinEventRepository.save(joinEvent3);
    }

    @Test
    @Rollback
    public void getUserCheckIn() {
        int eid = event.geteId();
        List<UserCheckIn> userCheckInList = userService.getUserCheckIn(eid);
        assertEquals(userCheckInList.size(), 2);

        UserCheckIn userCheckIn1 = userCheckInList.get(0);
        assertEquals(userCheckIn1.getName(), "aa");
        assertEquals(userCheckIn1.getUid(), u2.getuId());
        assertEquals(userCheckIn1.isType(), true);

        UserCheckIn userCheckIn2 = userCheckInList.get(1);
        assertEquals(userCheckIn2.getName(), "bb");
        assertEquals(userCheckIn2.getUid(), u3.getuId());
        assertEquals(userCheckIn2.isType(), false);

    }

    @After
    public void tearDown() {
        eventRepository.delete(event);
        userRepository.delete(u1);
        userRepository.delete(u2);
        userRepository.delete(u3);
        joinEventRepository.delete(joinEvent1);
        joinEventRepository.delete(joinEvent2);
        joinEventRepository.delete(joinEvent3);

    }
}
