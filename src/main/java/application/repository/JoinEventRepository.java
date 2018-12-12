package application.repository;

import application.entity.JoinEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JoinEventRepository extends CrudRepository<JoinEvent, Long> {
    /**
     * 通过jeId返回JoinEvent
     *
     * @param jeId
     * @return
     */
    JoinEvent findByJeId(int jeId);

    /**
     * 返回某一用户参加的JoinEvent
     *
     * @param uId
     * @return
     */
    List<JoinEvent> findByUId(int uId);

    /**
     * 返回包含某个活动的JoinEvent
     *
     * @param eId
     * @return
     */
    List<JoinEvent> findByEId(int eId);

    /**
     * 返回签到状态为特定值的JoinEvent
     *
     * @param jeState
     * @return
     */
    List<JoinEvent> findByJeState(String jeState);

    /**
     * 根据jeId删除
     *
     * @param jeId
     */
    void deleteByJeId(int jeId);

    /**
     * 根据用户ID删除
     *
     * @param uId
     */
    void deleteByUId(int uId);

    /**
     * 根据活动ID删除
     *
     * @param eId
     */
    void deleteByEId(int eId);

    /**
     * 删除所有对应关系
     *
     * @param joinEvent
     */
    @Override
    void delete(JoinEvent joinEvent);

    @Query(value = "SELECT u_id FROM join_event WHERE e_id = ?", nativeQuery = true)
    List<Integer> getParticipantsByEId(int eid);

    @Query(value = "SELECT * FROM join_event WHERE e_id = ?", nativeQuery = true)
    List<JoinEvent> getParticipantsByEId2(int eid);

    JoinEvent findByUIdAndEId(int uid, int eid);
}
