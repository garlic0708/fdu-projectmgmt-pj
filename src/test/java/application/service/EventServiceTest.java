package application.service;

import application.SpringBootWebApplication;
import application.entity.Event;
import application.entity.EventSlide;
import application.repository.EventRepository;
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

    private List<Event> events;

    @Before
    public void setUp() {
        events = new LinkedList<>();
        Event event = new Event();
        event.setEventname("event1");
        event.setImage("path/image1");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventname("event2");
        event.setImage("path/image2");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventname("event3");
        event.setImage("path/image3");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventname("event4");
        event.setImage("path/image4");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventname("event5");
        event.setImage("path/image5");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventname("event6");
        event.setImage("path/image6");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventname("event7");
        event.setImage("path/image7");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventname("event8");
        event.setImage("path/image8");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventname("event9");
        event.setImage("path/image9");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventname("event10");
        event.setImage("path/image10");
        event = eventRepository.save(event);
        events.add(event);

        event = new Event();
        event.setEventname("event11");
        event.setImage("path/image11");
        event = eventRepository.save(event);
        events.add(event);

    }

    @Test
    @Rollback
    public void getHomeSlides() throws Exception {
        List<EventSlide> eventSlides = eventService.getHomeSlides();
        assertEquals(eventSlides.size(), 3);
        assertEquals(eventSlides.get(0).getPath(), "path/image1");
        assertEquals(eventSlides.get(1).getPath(), "path/image2");
        assertEquals(eventSlides.get(2).getPath(), "path/image3");
    }

    @Test
    @Rollback
    public void getHomeFlow() throws Exception {
        List<EventSlide> eventSlides = eventService.getHomeFlow();
        assertEquals(eventSlides.size(), 10);
        for (int i = 1; i <= 10; i++) {
            assertEquals(eventSlides.get(i-1).getPath(), "path/image" + i);
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

    }
}