package entity;

import javax.persistence.*;

@Entity
@Table(name = "event_tag", schema = "yueya", catalog = "")
public class EventTagEntity {
    private int etId;
    private Integer eId;
    private Integer tId;

    @Id
    @Column(name = "et_id")
    public int getEtId() {
        return etId;
    }

    public void setEtId(int etId) {
        this.etId = etId;
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
    @Column(name = "t_id")
    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventTagEntity that = (EventTagEntity) o;

        if (etId != that.etId) return false;
        if (eId != null ? !eId.equals(that.eId) : that.eId != null) return false;
        if (tId != null ? !tId.equals(that.tId) : that.tId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = etId;
        result = 31 * result + (eId != null ? eId.hashCode() : 0);
        result = 31 * result + (tId != null ? tId.hashCode() : 0);
        return result;
    }
}
