package application.entity.userSecurity;

import javax.persistence.*;

/**
 * Creator: DreamBoy
 * Date: 2018/12/14.
 */
@Entity
@Table(name = "password_reset", schema = "yueya", catalog = "")
public class PasswordReset {
    private int prId;
    private Integer uid;
    private String code;

    @Id
    @Column(name = "pr_id")
    public int getPrId() {
        return prId;
    }

    public void setPrId(int prId) {
        this.prId = prId;
    }

    @Basic
    @Column(name = "uid")
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordReset that = (PasswordReset) o;

        if (prId != that.prId) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = prId;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }
}
