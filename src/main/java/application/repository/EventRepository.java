package application.repository;

import application.entity.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    /**
     * 通过eId返回活动
     *
     * @return
     */
    Event findByEId(int eId);

    /**
     * 通过活动名称返回活动列表
     *
     * @param eventname
     * @return
     */
    List<Event> findByEventname(String eventname);

    /**
     * 通过活动内容返回活动列表
     *
     * @param eventname
     * @return
     */
    List<Event> findByContent(String eventname);

    /**
     * 通过活动发起者返回活动列表
     *
     * @param initiator
     * @return
     */
    List<Event> findByInitiator(Integer initiator);

    /**
     * 通过地点返回活动列表
     *
     * @param address
     * @return
     */
    List<Event> findByAddress(Integer address);

    /**
     * 通过活动状态返回活动列表
     *
     * @param state
     * @return
     */
    List<Event> findByEventstate(String state);

    /**
     * 列出所有活动
     */
    @Query(value = "select * from event", nativeQuery = true)
    List<Event> listEvents();

    /**
     * 根据eId删除活动
     *
     * @param eId
     */
    void deleteByEId(int eId);

    /**
     * 根据活动发起者删除活动
     *
     * @param initiator
     */
    void deleteByInitiator(Integer initiator);

    /**
     * 根据活动发起者删除活动
     *
     * @param eventstate
     */
    void deleteByEventstate(String eventstate);

    /**
     * 删除活动
     *
     * @param event
     */
    @Override
    void delete(Event event);
}