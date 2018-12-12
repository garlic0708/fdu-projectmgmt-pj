package application.entity.forms;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Creator: DreamBoy
 * Date: 2018/11/25.
 */
public class EventSlide {
    private String path; //event 的图片地址 image
    @JsonView(View.SimpleEvent.class)
    private String title; // event name
    @JsonView(View.SimpleEvent.class)
    private int id; // e_id
    @JsonView(View.SimpleEvent.class)
    private String status;

    public EventSlide() {}

    public EventSlide(String path,String title,int id){
        this.path=path;
        this.title=title;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
