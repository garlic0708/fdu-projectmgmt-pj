package application.entity.forms;

import application.entity.Address;
import application.entity.Tag;
import com.fasterxml.jackson.annotation.JsonView;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Creator: DreamBoy
 * Date: 2018/11/25.
 */
public class EventDetail {
    @JsonView(View.NearByEvent.class)
    private int eId;
    @JsonView(View.NearByEvent.class)
    private String eventName;
    private String content;
    private Timestamp startTime;
    private Timestamp endTime;
    @JsonView(View.NearByEvent.class)
    private Address address;
    private String eventState;
    private Boolean limited;  // 如果这一个是false，下面三个limit不需要设置
    private Integer creditLimit;
    private Integer upperLimit;
    private Integer lowerLimit;
    private String image;

    private List<Tag> tags; //从数据库表tag和eventTag中获取
    private Map<String, Object> initiator; //参考下面的注释
    private int currentAttendants; //当前参加人数

    public EventDetail() {}

    public EventDetail(int eId, String eventName, String content, Timestamp startTime, Timestamp endTime, Address address,
                       String eventState, Boolean limited, Integer creditLimit, Integer upperLimit, Integer lowerLimit, String image){
        this.eId=eId;
        this.eventName = eventName;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.eventState = eventState;
        this.limited = limited;
        this.creditLimit = creditLimit;
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

    public Map<String, Object> getInitiator() {
        return initiator;
    }

    public void setInitiator(Map<String, Object> initiator) {
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

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Boolean getLimited() {
        return limited;
    }

    public void setLimited(Boolean limited) {
        this.limited = limited;
    }

    public String getEventState() {
        return eventState;
    }

    public void setEventState(String eventState) {
        this.eventState = eventState;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int geteId() {
        return eId;
    }

    public void seteId(int eId) {
        this.eId = eId;
    }
}
