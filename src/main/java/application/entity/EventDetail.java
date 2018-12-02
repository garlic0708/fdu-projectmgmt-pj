package application.entity;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creator: DreamBoy
 * Date: 2018/11/25.
 */
public class EventDetail {
    private int eId;
    private String eventname;
    private String content;
    private Timestamp starttime;
    private Timestamp endtime;
    private Integer address;
    private String eventstate;
    private Byte limited;
    private Integer credictLimit;
    private Integer upperLimit;
    private Integer lowerLimit;
    private String image;

    private List<Tag> tags;
    private Map<String, String> initiator;
    private int currentAttendants;

//    public EventDetail() {
//        initiator = new HashMap<>();
//        initiator.put("name", "tangxh");
//        initiator.put("avatar", "url");
//    }

    public int getCurrentAttendants() {
        return currentAttendants;
    }

    public void setCurrentAttendants(int currentAttendants) {
        this.currentAttendants = currentAttendants;
    }

    public Map<String, String> getInitiator() {
        return initiator;
    }

    public void setInitiator(Map<String, String> initiator) {
        this.initiator = initiator;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Integer getCredictLimit() {
        return credictLimit;
    }

    public void setCredictLimit(Integer credictLimit) {
        this.credictLimit = credictLimit;
    }

    public Byte getLimited() {
        return limited;
    }

    public void setLimited(Byte limited) {
        this.limited = limited;
    }

    public String getEventstate() {
        return eventstate;
    }

    public void setEventstate(String eventstate) {
        this.eventstate = eventstate;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public int geteId() {
        return eId;
    }

    public void seteId(int eId) {
        this.eId = eId;
    }
}
