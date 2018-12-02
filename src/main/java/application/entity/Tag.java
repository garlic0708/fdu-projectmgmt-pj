package application.entity;

import javax.persistence.*;

/**
 * Creator: DreamBoy
 * Date: 2018/10/31.
 */
@Entity
public class Tag {
    private int tId;
    private String tagname;

    @Id
    @GeneratedValue
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

        Tag tag = (Tag) o;

        if (tId != tag.tId) return false;
        if (tagname != null ? !tagname.equals(tag.tagname) : tag.tagname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tId;
        result = 31 * result + (tagname != null ? tagname.hashCode() : 0);
        return result;
    }
}
