package application.entity.forms;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/12/3.
 */
public class AddEventForm {
    private String eventName;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssZ")
    private Timestamp startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssZ")
    private Timestamp endTime;
    private String poiId;
    private String addressName; // eg. 高科苑
    private String addressLocation; //eg. 蔡伦路1433弄
    private double addressPx;
    private double addressPy;
    private Integer creditLimit;
    private Integer upperLimit;
    private Integer lowerLimit;
    private String image;
    private List<Integer> tags;

    public AddEventForm() {}

    public AddEventForm(String eventName, String content, Timestamp startTime, Timestamp endTime,String poiId, String addressName, String addressLocation, double addressPx, double addressPy, Integer creditLimit, Integer upperLimit, Integer lowerLimit, String image, List<Integer> tags) {
        this.eventName = eventName;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.poiId = poiId;
        this.addressName = addressName;
        this.addressLocation = addressLocation;
        this.addressPx = addressPx;
        this.addressPy = addressPy;
        this.creditLimit = creditLimit;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.image = image;
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

    public double getAddressPy() {
        return addressPy;
    }

    public void setAddressPy(double addressPy) {
        this.addressPy = addressPy;
    }

    public double getAddressPx() {
        return addressPx;
    }

    public void setAddressPx(double addressPx) {
        this.addressPx = addressPx;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
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

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(String addressLocation) {
        this.addressLocation = addressLocation;
    }
}
