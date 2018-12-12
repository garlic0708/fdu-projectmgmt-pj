package application.service;

import application.entity.forms.AddEventForm;
import application.entity.Event;
import application.entity.forms.EventDetail;
import application.entity.forms.EventSlide;
import application.exception.AddEventException;
import application.exception.CancelEventException;

import java.util.List;
import java.util.Map;

/**
 * Creator: DreamBoy
 * Date: 2018/11/25.
 */
public interface EventService {
    /**
     * 通过eid，拿到活动的详细信息
     * @param eid
     * @return
     */
    Map<String, Object> getEventDetailById(int eid, int uid);

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

    void cancelEvent(int uid, int eid) throws CancelEventException;

    List<EventDetail> getNearbyEvents(double x1, double y1, double x2, double y2);

    Event getById(int eid);

    /**
     * 通过uid，返回这个用户参加过的活动
     * @param uid
     * @return
     */
    List<EventSlide> getEventsJoined(int uid);

    /**
     * 通过uid，返回这个用户发起过的活动
     * @param uid
     * @return
     */
    List<EventSlide> getEventsReleased(int uid);
}
