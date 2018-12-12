package entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "event", schema = "yueya", catalog = "")
public class EventEntity {
    private int eId;
    private String eventname;
    private String content;
    private Timestamp starttime;
    private Timestamp endtime;
    private Integer address;
    private Integer initiator;
    private String eventstate;

    @Id
    @Column(name = "e_id")
    public int geteId() {
        return eId;
    }

    public void seteId(int eId) {
        this.eId = eId;
    }

    @Basic
    @Column(name = "eventname")
    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
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
    @Column(name = "starttime")
    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    @Basic
    @Column(name = "endtime")
    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    @Basic
    @Column(name = "address")
    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    @Basic
    @Column(name = "initiator")
    public Integer getInitiator() {
        return initiator;
    }

    public void setInitiator(Integer initiator) {
        this.initiator = initiator;
    }

    @Basic
    @Column(name = "eventstate")
    public String getEventstate() {
        return eventstate;
    }

    public void setEventstate(String eventstate) {
        this.eventstate = eventstate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventEntity that = (EventEntity) o;

        if (eId != that.eId) return false;
        if (eventname != null ? !eventname.equals(that.eventname) : that.eventname != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (starttime != null ? !starttime.equals(that.starttime) : that.starttime != null) return false;
        if (endtime != null ? !endtime.equals(that.endtime) : that.endtime != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (initiator != null ? !initiator.equals(that.initiator) : that.initiator != null) return false;
        if (eventstate != null ? !eventstate.equals(that.eventstate) : that.eventstate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eId;
        result = 31 * result + (eventname != null ? eventname.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (initiator != null ? initiator.hashCode() : 0);
        result = 31 * result + (eventstate != null ? eventstate.hashCode() : 0);
        return result;
    }
}
