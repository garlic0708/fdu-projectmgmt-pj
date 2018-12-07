package application.entity.forms;

import application.entity.Tag;

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
    private String address; // addressName
    private String eventstate;
    private Boolean limited;  // 如果这一个是false，下面三个limit不需要设置
    private Integer credictLimit;
    private Integer upperLimit;
    private Integer lowerLimit;
    private String image;

    private List<Tag> tags; //从数据库表tag和eventTag中获取
    private Map<String, String> initiator; //参考下面的注释
    private int currentAttendants; //当前参加人数

    public EventDetail(int eId,String eventname,String content,Timestamp starttime,Timestamp endtime,String address,
    String eventstate,Boolean limited,Integer credictLimit,Integer upperLimit,Integer lowerLimit,String image){
        this.eId=eId;
        this.eventname=eventname;
        this.content = content;
        this.starttime = starttime;
        this.endtime = endtime;
        this.address = address;
        this.eventstate = eventstate;
        this.limited = limited;
        this.credictLimit = credictLimit;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.image = image;
    }
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

    public Boolean getLimited() {
        return limited;
    }

    public void setLimited(Boolean limited) {
        this.limited = limited;
    }

    public String getEventstate() {
        return eventstate;
    }

    public void setEventstate(String eventstate) {
        this.eventstate = eventstate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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
