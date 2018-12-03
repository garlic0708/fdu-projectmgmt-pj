package application.service;

import application.entity.Event;

/**
 * Creator: DreamBoy
 * Date: 2018/12/3.
 */
public interface QuartzEventService {
    void addEventJob(Event event);
}
