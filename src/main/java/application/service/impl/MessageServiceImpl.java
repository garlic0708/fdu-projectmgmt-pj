package application.service.impl;

import application.entity.Message;
import application.exception.ReadMessageException;
import application.repository.MessageRepository;
import application.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/12/5.
 */
@Service
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getMessagesByUid(int uid) {
        return messageRepository.findByReceiver(uid);
    }

    public void readMessage(int uid, int mid) throws ReadMessageException {
        Message message = messageRepository.findByMId(mid);
        if (message == null)
            throw new ReadMessageException("No such message");
        if (message.getReceiver() != uid)
            throw new ReadMessageException("Permission denied");
        message.setMessageState(Message.READ);
    }
}
