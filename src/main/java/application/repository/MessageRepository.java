package application.repository;

import application.entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    /**
     * 通过m_id返回信息
     * @param mID
     * @return
     */
    Message findByMId(int mID);

    /**
     * 通过信息状态返回信息列表
     *
     * @param messagestate
     * @return
     */
    List<Message> findByMessagestate(String messagestate);
}