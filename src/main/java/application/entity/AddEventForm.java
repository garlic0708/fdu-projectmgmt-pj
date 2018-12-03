package application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Creator: DreamBoy
 * Date: 2018/12/3.
 */
public class AddEventForm {
    private String eventname;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp starttime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp endtime;
    private String addressName;
    private double addressPx;
    private double addressPy;
    private Integer credictLimit;
    private Integer upperLimit;
    private Integer lowerLimit;
    private String image;

    public AddEventForm() {}

    public AddEventForm(String eventname, String content, Timestamp starttime, Timestamp endtime, String addressName, double addressPx, double addressPy, Integer credictLimit, Integer upperLimit, Integer lowerLimit, String image) {
        this.eventname = eventname;
        this.content = content;
        this.starttime = starttime;
        this.endtime = endtime;
        this.addressName = addressName;
        this.addressPx = addressPx;
        this.addressPy = addressPy;
        this.credictLimit = credictLimit;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.image = image;
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
}
