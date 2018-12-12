package entity;

import javax.persistence.*;

@Entity
@Table(name = "tag", schema = "yueya", catalog = "")
public class TagEntity {
    private int tId;
    private String tagname;

    @Id
    @Column(name = "t_id")
    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    @Basic
    @Column(name = "tagname")
    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagEntity tagEntity = (TagEntity) o;

        if (tId != tagEntity.tId) return false;
        if (tagname != null ? !tagname.equals(tagEntity.tagname) : tagEntity.tagname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tId;
        result = 31 * result + (tagname != null ? tagname.hashCode() : 0);
        return result;
    }
}
