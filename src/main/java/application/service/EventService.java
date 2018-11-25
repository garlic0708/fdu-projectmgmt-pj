package application.service;

import application.entity.Event;
import application.entity.EventDetail;
import application.entity.EventSlide;

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
}
