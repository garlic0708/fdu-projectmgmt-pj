package entity;

import javax.persistence.*;

@Entity
@Table(name = "start_event", schema = "yueya", catalog = "")
public class StartEventEntity {
    private int seId;
    private Integer uId;
    private Integer eId;

    @Id
    @Column(name = "se_id")
    public int getSeId() {
        return seId;
    }

    public void setSeId(int seId) {
        this.seId = seId;
    }

    @Basic
    @Column(name = "u_id")
    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    @Basic
    @Column(name = "e_id")
    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StartEventEntity that = (StartEventEntity) o;

        if (seId != that.seId) return false;
        if (uId != null ? !uId.equals(that.uId) : that.uId != null) return false;
        if (eId != null ? !eId.equals(that.eId) : that.eId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = seId;
        result = 31 * result + (uId != null ? uId.hashCode() : 0);
        result = 31 * result + (eId != null ? eId.hashCode() : 0);
        return result;
    }
}
