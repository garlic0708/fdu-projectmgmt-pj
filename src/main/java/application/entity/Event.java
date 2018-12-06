package application.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Creator: DreamBoy
 * Date: 2018/11/24.
 */
@Entity
public class Event {
    @GeneratedValue
    private int eId;
    private String eventName;

    private String content;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer address;
    private Integer initiator;
    private String eventState; // notStarted , started, ended, canceled

    private Boolean limited;
    private Integer creditLimit;
    private Integer upperLimit;
    private Integer lowerLimit;
    private String image;

    @Id
    @GeneratedValue
    @Column(name = "e_id")
    public int geteId() {
        return eId;
    }

    public void seteId(int eId) {
        this.eId = eId;
    }

    @Basic
    @Column(name = "eventname")
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventname) {
        this.eventName = eventname;
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
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp starttime) {
        this.startTime = starttime;
    }

    @Basic
    @Column(name = "endtime")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endtime) {
        this.endTime = endtime;
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
    public String getEventState() {
        return eventState;
    }

    public void setEventState(String eventstate) {
        this.eventState = eventstate;
    }

    @Basic
    @Column(name = "limited")
    public Boolean getLimited() {
        return limited;
    }

    public void setLimited(Boolean limited) {
        this.limited = limited;
    }

    @Basic
    @Column(name = "credit_limit")
    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer credictLimit) {
        this.creditLimit = credictLimit;
    }

    @Basic
    @Column(name = "upper_limit")
    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    @Basic
    @Column(name = "lower_limit")
    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (eId != event.eId) return false;
        if (eventName != null ? !eventName.equals(event.eventName) : event.eventName != null) return false;
        if (content != null ? !content.equals(event.content) : event.content != null) return false;
        if (startTime != null ? !startTime.equals(event.startTime) : event.startTime != null) return false;
        if (endTime != null ? !endTime.equals(event.endTime) : event.endTime != null) return false;
        if (address != null ? !address.equals(event.address) : event.address != null) return false;
        if (initiator != null ? !initiator.equals(event.initiator) : event.initiator != null) return false;
        if (eventState != null ? !eventState.equals(event.eventState) : event.eventState != null) return false;
        if (limited != null ? !limited.equals(event.limited) : event.limited != null) return false;
        if (creditLimit != null ? !creditLimit.equals(event.creditLimit) : event.creditLimit != null) return false;
        if (upperLimit != null ? !upperLimit.equals(event.upperLimit) : event.upperLimit != null) return false;
        if (lowerLimit != null ? !lowerLimit.equals(event.lowerLimit) : event.lowerLimit != null) return false;
        if (image != null ? !image.equals(event.image) : event.image != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eId;
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (initiator != null ? initiator.hashCode() : 0);
        result = 31 * result + (eventState != null ? eventState.hashCode() : 0);
        result = 31 * result + (limited != null ? limited.hashCode() : 0);
        result = 31 * result + (creditLimit != null ? creditLimit.hashCode() : 0);
        result = 31 * result + (upperLimit != null ? upperLimit.hashCode() : 0);
        result = 31 * result + (lowerLimit != null ? lowerLimit.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
