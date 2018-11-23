package entity;

import javax.persistence.*;

@Entity
@Table(name = "join_event", schema = "yueya", catalog = "")
public class JoinEventEntity {
    private int jeId;
    private Integer uId;
    private Integer eId;
    private String jeState;

    @Id
    @Column(name = "je_id")
    public int getJeId() {
        return jeId;
    }

    public void setJeId(int jeId) {
        this.jeId = jeId;
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

    @Basic
    @Column(name = "je_state")
    public String getJeState() {
        return jeState;
    }

    public void setJeState(String jeState) {
        this.jeState = jeState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JoinEventEntity that = (JoinEventEntity) o;

        if (jeId != that.jeId) return false;
        if (uId != null ? !uId.equals(that.uId) : that.uId != null) return false;
        if (eId != null ? !eId.equals(that.eId) : that.eId != null) return false;
        if (jeState != null ? !jeState.equals(that.jeState) : that.jeState != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jeId;
        result = 31 * result + (uId != null ? uId.hashCode() : 0);
        result = 31 * result + (eId != null ? eId.hashCode() : 0);
        result = 31 * result + (jeState != null ? jeState.hashCode() : 0);
        return result;
    }
}
