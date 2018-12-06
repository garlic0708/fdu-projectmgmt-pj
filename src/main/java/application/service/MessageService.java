package application.service;

import application.entity.Message;

import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/12/5.
 */
public interface MessageService {
    /**
     * 通过uid，得到该用户的消息列表
     * @param uid
     * @return
     */
    List<Message> getMessagesByUid(int uid);
}
