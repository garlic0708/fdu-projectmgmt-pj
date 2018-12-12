package application.repository;

import application.entity.EventTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventTagRepository extends CrudRepository<EventTag, Long> {
    /**
     * 通过etId返回活动和标签的对应关系
     *
     * @param etId
     * @return
     */
    EventTag findByEtId(int etId);

    /**
     * 返回含有某个活动的对应关系列表
     *
     * @param eId
     * @return
     */
    List<EventTag> findByEId(int eId);

    /**
     * 返回含有某一标签的对应关系列表
     *
     * @param tId
     * @return
     */
    List<EventTag> findByTId(int tId);

    /**
     * 根据etId删除
     *
     * @param etId
     */
    void deleteByEtId(int etId);

    /**
     * 根据活动ID删除
     *
     * @param eId
     */
    void deleteByEId(int eId);

    /**
     * 根据标签ID删除
     *
     * @param tId
     */
    void deleteByTId(int tId);

    /**
     * 删除所有对应关系
     *
     * @param eventTag
     */
    @Override
    void delete(EventTag eventTag);
}
