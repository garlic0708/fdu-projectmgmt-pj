package application.service.impl;

import application.entity.Message;
import application.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/12/5.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public List<Message> getMessagesByUid(int uid) {
        //TODO
        return null;
    }
}
