package application.service;

import application.SpringBootWebApplication;
import application.entity.*;
import application.entity.forms.EventDetail;
import application.entity.forms.EventSlide;
import application.repository.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {SpringBootWebApplication.class})
public class EventServiceTest {


    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JoinEventRepository joinEventRepository;
    @Autowired
    private EventTagRepository eventTagRepository;

    private List<Event> events;

    private Address address;
    private List<JoinEvent> joinEvents;
    private List<User> users;
    private List<Tag> tags;
    private List<EventTag> eventTags;

    @Before
    public void setUp() {
        events = new LinkedList<>();
        joinEvents = new LinkedList<>();
        users = new LinkedList<>();
        tags = new LinkedList<>();
        eventTags = new LinkedList<>();

        Tag tag1 = new Tag();
        tag1.setTagname("tag1");
        tag1 = tagRepository.save(tag1);
        tags.add(tag1);
        Tag tag2 = new Tag();
        tag2.setTagname("tag2");
        tag2 = tagRepository.save(tag2);
        tags.add(tag2);
        Tag tag3 = new Tag();
        tag3.setTagname("tag3");
        tag3 = tagRepository.save(tag3);
        tags.add(tag3);

        User u1 = new User();
        u1.setNickname("gtx");
        u1.setImage("path1");
        u1 = userRepository.save(u1);
        users.add(u1);

        User u2 = new User();
        u2.setNickname("aa");
        u2.setImage("path2");
        u2 = userRepository.save(u2);
        users.add(u2);

        User u3 = new User();
        u3.setNickname("bb");
        u3.setImage("path3");
        u3 = userRepository.save(u3);
        users.add(u3);

        address=new Address();
        address.setAddressName("Shanghai");
        address = new Address();
        address.setAddressName("Shanghai");
        address.setPositionX(10.0);
        address.setPositionY(15.8);
        address = addressRepository.save(address);
        int aid = address.getAddrId();

        Event event = new Event();
        event.setEventName("event1");
        event.setImage("path/image1");
        event.setContent("content1");
        event.setAddress(aid);
        event.setCreditLimit(10);
        event.setEventState("end");
        event.setInitiator(u1.getuId());
        event = eventRepository.save(event);
        events.add(event);

        JoinEvent joinEvent1 = new JoinEvent();
        joinEvent1.setuId(u1.getuId());
        joinEvent1.setJeState("initiator");
        joinEvent1.seteId(event.geteId());
        joinEvent1 = joinEventRepository.save(joinEvent1);
        joinEvents.add(joinEvent1);

        JoinEvent joinEvent2 = new JoinEvent();
        joinEvent2.setuId(u2.getuId());
        joinEvent2.seteId(event.geteId());
        joinEvent2.setJeState("participated");
        joinEvent2 = joinEventRepository.save(joinEvent2);
        joinEvents.add(joinEvent2);

        EventTag eventTag = new EventTag();
        eventTag.seteId(event.geteId());
        eventTag.settId(tag1.gettId());
        eventTag = eventTagRepository.save(eventTag);
        eventTags.add(eventTag);

        eventTag = new EventTag();
        eventTag.seteId(event.geteId());
        eventTag.settId(tag3.gettId());
        eventTag = eventTagRepository.save(eventTag);
        eventTags.add(eventTag);

        event = new Event();
        event.setEventName("event2");
        event.setImage("path/image2");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventName("event3");
        event.setImage("path/image3");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventName("event4");
        event.setImage("path/image4");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventName("event5");
        event.setImage("path/image5");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventName("event6");
        event.setImage("path/image6");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventName("event7");
        event.setImage("path/image7");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventName("event8");
        event.setImage("path/image8");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventName("event9");
        event.setImage("path/image9");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventName("event10");
        event.setImage("path/image10");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventName("event11");
        event.setImage("path/image11");
        event = eventRepository.save(event);
        events.add(event);

    }

//    @Test
//    @Rollback
//    public void getEventDetailById(){
//        int eid=events.get(0).geteId();
//        EventDetail eventDetail=eventService.getEventDetailById(eid, 0);
//        assertEquals(eventDetail.getAddress(),"Shanghai");
//        assertEquals(eventDetail.getContent(),"content1");
//        assertEquals(eventDetail.getCreditLimit(),new Integer(10));
//        assertEquals(eventDetail.getImage(),"path/image1");
//        assertEquals(eventDetail.getEventState(),"end");
//
//    }

    @Test
    @Rollback
    public void getHomeSlides() throws Exception {
        List<EventSlide> eventSlides = eventService.getHomeSlides();
        assertEquals(eventSlides.size(), 3);
        assertEquals(eventSlides.get(0).getPath(), "path/image11");
        assertEquals(eventSlides.get(1).getPath(), "path/image10");
        assertEquals(eventSlides.get(2).getPath(), "path/image9");
    }

    @Test
    @Rollback
    public void getHomeFlow() throws Exception {
        List<EventSlide> eventSlides = eventService.getHomeFlow();
        assertEquals(eventSlides.size(), 10);
        for (int i = 0; i < 10; i++) {
            assertEquals(eventSlides.get(i).getPath(), "path/image" + (11-i));
        }
    }

    @Test
    public void getParticipants() throws Exception {
        List<Integer> list = eventService.getParticipants(1);
        assertEquals(list.size(), 0);
    }

    @After
    public void tearDown() {
        for (int i = 0; i < events.size(); i++) {
            eventRepository.delete(events.get(i));
        }
        for (int i = 0; i < users.size(); i++) {
            userRepository.delete(users.get(i));
        }
        for (int i = 0; i < joinEvents.size(); i++) {
            joinEventRepository.delete(joinEvents.get(i));
        }
        for (int i = 0; i < tags.size(); i++) {
            tagRepository.delete(tags.get(i));
        }
        for (int i = 0; i < eventTags.size(); i++) {
            eventTagRepository.delete(eventTags.get(i));
        }
        addressRepository.delete(address);

    }
}