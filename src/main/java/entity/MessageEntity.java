package entity;

import javax.persistence.*;

@Entity
@Table(name = "message", schema = "yueya", catalog = "")
public class MessageEntity {
    private int mId;
    private Integer sender;
    private Integer receiver;
    private String content;
    private String messagestate;

    @Id
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
    public String getMessagestate() {
        return messagestate;
    }

    public void setMessagestate(String messagestate) {
        this.messagestate = messagestate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageEntity that = (MessageEntity) o;

        if (mId != that.mId) return false;
        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        if (receiver != null ? !receiver.equals(that.receiver) : that.receiver != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (messagestate != null ? !messagestate.equals(that.messagestate) : that.messagestate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mId;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (messagestate != null ? messagestate.hashCode() : 0);
        return result;
    }
}
