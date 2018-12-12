package application.entity;

import javax.persistence.*;

/**
 * Creator: DreamBoy
 * Date: 2018/10/31.
 */
@Entity
public class Message {
    public static final String READ = "read";
    public static final String UNREAD = "unread";
    private int mId;
    private Integer sender;
    private Integer receiver;
    private String content;
    private String messageState; // read、 unread

    @Id
    @GeneratedValue
    @Column(name = "m_id")
    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    @Basic
    @Column(name = "sender")
    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    @Basic
    @Column(name = "receiver")
    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "messagestate")
    public String getMessageState() {
        return messageState;
    }

    public void setMessageState(String messagestate) {
        this.messageState = messagestate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (mId != message.mId) return false;
        if (sender != null ? !sender.equals(message.sender) : message.sender != null) return false;
        if (receiver != null ? !receiver.equals(message.receiver) : message.receiver != null) return false;
        if (content != null ? !content.equals(message.content) : message.content != null) return false;
        if (messageState != null ? !messageState.equals(message.messageState) : message.messageState != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mId;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (messageState != null ? messageState.hashCode() : 0);
        return result;
    }
}
