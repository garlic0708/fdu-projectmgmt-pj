package application.entity.forms;

/**
 * Creator: DreamBoy
 * Date: 2018/12/5.
 */
public class UserCheckIn {
    private int uid; // user id
    private String name; // user nickName
    private boolean type; // 参与情况，如果是PARTICIPATED，那就是false，如果是CHECK，那就是true

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
