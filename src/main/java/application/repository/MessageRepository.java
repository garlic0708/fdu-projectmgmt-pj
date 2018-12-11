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
     * 通过uid，返回用户所有的消息列表
     * @param uid
     * @return
     */
    List<Message> findByReceiver(int uid);

    /**
     * 通过信息状态返回信息列表
     *
     * @param messagestate
     * @return
     */
    List<Message> findByMessageState(String messagestate);
}