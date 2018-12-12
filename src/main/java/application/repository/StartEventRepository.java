package application.repository;

import application.entity.StartEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StartEventRepository extends CrudRepository<StartEvent, Long> {
    /**
     * 通过se_id返回开始活动
     * @param seID
     * @return
     */
    StartEvent findBySeId(int seID);

    /**
     * 通过user_id返回开始活动列表
     *
     * @param uID
     * @return
     */
   // List<StartEvent> findByUID(int uID);

    /**
     * 根据eID列出所有用户
     */
    @Query(value = "select u_id from startevent where e_id=eID", nativeQuery = true)
    List<StartEvent> listUsers(int eID);
}