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
     * @param eventName
     * @return
     */
    List<Event> findByEventName(String eventName);

    /**
     * 通过活动内容返回活动列表
     *
     * @param content
     * @return
     */
    List<Event> findByContent(String content);

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
    List<Event> findByEventState(String state);

    /**
     * 列出所有活动
     */
    @Query(value = "select * from event", nativeQuery = true)
    List<Event> listEvents();

    /**
     * 通过两组xy坐标，找到在该坐标内的活动列表
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 活动列表
     */
    @Query(value = "SELECT * FROM event INNER JOIN address on event.address = address.addr_id " +
            "WHERE positionX > ? AND positionY > ? AND positionX < ? AND positionY < ?" ,nativeQuery = true)
    List<Event> getEventsInASquare(double x1, double y1, double x2, double y2);

    /**
     * 取后num个event
     * @param num
     * @return
     */
    @Query(value = "SELECT * FROM event  ORDER BY e_id DESC LIMIT ?" ,nativeQuery = true)
    List<Event> getEvents(int num);

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
     * @param eventState
     */
    void deleteByEventState(String eventState);

    /**
     * 删除活动
     *
     * @param event
     */
    @Override
    void delete(Event event);
}
