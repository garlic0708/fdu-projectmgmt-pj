package application.service.impl;

import application.entity.Event;
import application.entity.EventDetail;
import application.entity.EventSlide;
import application.repository.EventRepository;
import application.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventDetail getEventDetailById(int eid) {
        return null;
    }


    /**
     * 拿一个到三个数据， 拿前三个
     *
     * @return
     */
    @Override
    public List<EventSlide> getHomeSlides() {
        List<Event> events = eventRepository.getEvents(3);
        List<EventSlide> eventSlides = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            String path = events.get(i).getImage();
            String title = events.get(i).getEventname();
            int id = events.get(i).geteId();
            EventSlide eventSlide = new EventSlide(path, title, id);
            eventSlides.add(eventSlide);
        }
        return eventSlides;
    }

    /**
     * 拿前十个
     *
     * @return
     */
    @Override
    public List<EventSlide> getHomeFlow() {
        List<Event> events = eventRepository.getEvents(10);
        List<EventSlide> eventSlides = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            String path = events.get(i).getImage();
            String title = events.get(i).getEventname();
            int id = events.get(i).geteId();
            EventSlide eventSlide = new EventSlide(path, title, id);
            eventSlides.add(eventSlide);
        }
        return eventSlides;
    }

}
