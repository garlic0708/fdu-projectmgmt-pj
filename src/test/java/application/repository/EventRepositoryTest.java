package application.repository;

import application.entity.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    @Rollback
    public void findByAddrId() {
        Event event = new Event();
        event.setEventName("Basketball");
        event.setContent("Play basketball");
        event.setAddress(1);
        eventRepository.save(event);
        int id = event.geteId();
        Event test = eventRepository.findByEId(id);
        assertEquals(test.getEventName(), "Basketball");
    }

    @Test
    @Rollback
    public void findByEventname() {
        Event event = new Event();
        event.setEventName("Football");
        event.setContent("Play football");
        eventRepository.save(event);

        Event event1 = new Event();
        event1.setEventName("Basketball");
        event1.setContent("Play basketball");
        eventRepository.save(event1);

        Event event2 = new Event();
        event2.setEventName("Basketball");
        event2.setContent("Let's play basketball");
        eventRepository.save(event2);

        List<Event> list = eventRepository.findByEventName("Basketball");
        Event test1 = list.get(0);
        Event test2 = list.get(1);
        assertEquals(test1.getContent(), "Play basketball");
        assertEquals(test2.getContent(), "Let's play basketball");
    }

    public void findByContent() {
        Event event = new Event();
        event.setEventName("Football");
        event.setContent("Play football");
        eventRepository.save(event);

        Event event1 = new Event();
        event1.setEventName("Basketball");
        event1.setContent("Play basketball");
        eventRepository.save(event1);

        Event event2 = new Event();
        event2.setEventName("Basketball");
        event2.setContent("Let's play basketball");
        eventRepository.save(event2);

        List<Event> list = eventRepository.findByContent("Play football");
        Event test1 = list.get(0);
        assertEquals(test1.getEventName(), "Football");
    }

    @Test
    @Rollback
    public void findByInitiator() {
        Event event = new Event();
        event.setEventName("Football");
        event.setInitiator(1);
        eventRepository.save(event);

        Event event1 = new Event();
        event1.setEventName("Lecture");
        event1.setInitiator(2);
        eventRepository.save(event1);

        Event event2 = new Event();
        event2.setEventName("Basketball");
        event2.setInitiator(1);
        eventRepository.save(event2);

        List<Event> list = eventRepository.findByInitiator(1);
        Event test1 = list.get(0);
        Event test2 = list.get(1);
        assertEquals(test1.getEventName(), "Football");
        assertEquals(test2.getEventName(), "Basketball");
    }

    @Test
    @Rollback
    public void findByAddress() {
        Event event = new Event();
        event.setEventName("Football");
        event.setAddress(1);
        eventRepository.save(event);

        Event event1 = new Event();
        event1.setEventName("Lecture");
        event1.setAddress(2);
        eventRepository.save(event1);

        Event event2 = new Event();
        event2.setEventName("Basketball");
        event2.setAddress(1);
        eventRepository.save(event2);

        List<Event> list = eventRepository.findByAddress(1);
        Event test1 = list.get(0);
        Event test2 = list.get(1);
        assertEquals(test1.getEventName(), "Football");
        assertEquals(test2.getEventName(), "Basketball");
    }

    @Test
    @Rollback
    public void findByEventstate() {
        Event event = new Event();
        event.setEventName("Football");
        event.setEventState("start");
        eventRepository.save(event);

        Event event1 = new Event();
        event1.setEventName("Lecture");
        event1.setEventState("end");
        eventRepository.save(event1);

        Event event2 = new Event();
        event2.setEventName("Basketball");
        event2.setEventState("start");
        eventRepository.save(event2);

        List<Event> list = eventRepository.findByEventState("start");
        Event test1 = list.get(0);
        Event test2 = list.get(1);
        assertEquals(test1.getEventName(), "Football");
        assertEquals(test2.getEventName(), "Basketball");
    }

    @Test
    @Rollback
    public void listEvents() {
        Event event = new Event();
        event.setEventName("Football");
        event.setContent("Play football");
        eventRepository.save(event);

        Event event1 = new Event();
        event1.setEventName("Basketball");
        event1.setContent("Play basketball");
        eventRepository.save(event1);

        Event event2 = new Event();
        event2.setEventName("Basketball");
        event2.setContent("Let's play basketball");
        eventRepository.save(event2);

        List<Event> list = eventRepository.listEvents();
        int size = list.size();
        Event test1 = list.get(size-3);
        Event test2 = list.get(size-2);
        Event test3 = list.get(size-1);
        assertEquals(test1.getContent(), "Play football");
        assertEquals(test2.getContent(), "Play basketball");
        assertEquals(test3.getContent(), "Let's play basketball");
    }

    @Test
    @Rollback
    public void deleteByEId() {
        Event event = new Event();
        event.setEventName("Basketball");
        eventRepository.save(event);
        int id = event.geteId();
        eventRepository.deleteByEId(id);
        assertEquals(eventRepository.findByEId(id), null);
    }

    @Test
    @Rollback
    public void deleteByInitiator() {
        Event event = new Event();
        event.setInitiator(10);
        eventRepository.save(event);
        eventRepository.deleteByInitiator(10);
        assertEquals(eventRepository.findByInitiator(10).size(), 0);
    }

    @Test
    @Rollback
    public void deleteByEventstate() {
        Event event = new Event();
        event.setEventState("start");
        event.setContent("Play football");
        eventRepository.save(event);
        Event event1 = new Event();
        event1.setEventState("end");
        event1.setContent("Play basketball");
        eventRepository.save(event);
        Event event2 = new Event();
        event2.setEventState("end");
        event2.setContent("Let's play basketball");

        eventRepository.save(event);
        eventRepository.deleteByEventState("end");
        assertNotEquals(eventRepository.findByContent("Play football").size(), 0);
        assertEquals(eventRepository.findByContent("Play basketball").size(), 0);
        assertEquals(eventRepository.findByContent("Let's play basketball").size(), 0);
    }

    @Test
    @Rollback
    public void deleteEvent() {
        Event event = new Event();
        event.setEventName("Basketball");
        event.setContent("Play basketball");
        eventRepository.save(event);
        int id = event.geteId();
        eventRepository.delete(event);
        assertEquals(eventRepository.findByEId(id), null);
    }

    @Test
    @Rollback
    public void updateEvent() {
        Event event = new Event();
        event.setEventName("Basketball");
        eventRepository.save(event);
        int id = event.geteId();
        Event event1 = eventRepository.findByEId(id);
        event.setEventName("Football");
        eventRepository.save(event1);
        Event test = eventRepository.findByEId(id);
        assertEquals(test.getEventName(), "Football");
    }
}
