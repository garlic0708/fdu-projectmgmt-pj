package application.service;

import application.entity.forms.AddEventForm;
import application.entity.Event;
import application.entity.forms.EventDetail;
import application.entity.forms.EventSlide;
import application.exception.AddEventException;

import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/11/25.
 */
public interface EventService {
    EventDetail getEventDetailById(int eid);

    /**
     * 拿一个到三个数据， 拿前三个
     * @return
     */
    List<EventSlide> getHomeSlides();

    /**
     * 拿前十个
     * @return
     */
    List<EventSlide> getHomeFlow();

    List<Integer> getParticipants(int eid);

    void autoCancel(int eid);

    void markAsStarted(int eid);

    void markAsEnded(int eid);

    Event addEvent(AddEventForm form, int uid) throws AddEventException;
}
