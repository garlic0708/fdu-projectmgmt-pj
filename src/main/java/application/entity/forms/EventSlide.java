package application.entity.forms;

/**
 * Creator: DreamBoy
 * Date: 2018/11/25.
 */
public class EventSlide {
    private String path; //event 的图片地址 image
    private String title; // event name
    private int id; // e_id

    public EventSlide(String path,String title,int id){
        this.path=path;
        this.title=title;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }
}
