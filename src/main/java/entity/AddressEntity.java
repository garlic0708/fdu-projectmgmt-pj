package entity;

import javax.persistence.*;

@Entity
@Table(name = "address", schema = "yueya", catalog = "")
public class AddressEntity {
    private int addrId;
    private String addressname;
    private Double positionX;
    private Double positionY;

    @Id
    @Column(name = "addr_id")
    public int getAddrId() {
        return addrId;
    }

    public void setAddrId(int addrId) {
        this.addrId = addrId;
    }

    @Basic
    @Column(name = "addressname")
    public String getAddressname() {
        return addressname;
    }

    public void setAddressname(String addressname) {
        this.addressname = addressname;
    }

    @Basic
    @Column(name = "positionX")
    public Double getPositionX() {
        return positionX;
    }

    public void setPositionX(Double positionX) {
        this.positionX = positionX;
    }

    @Basic
    @Column(name = "positionY")
    public Double getPositionY() {
        return positionY;
    }

    public void setPositionY(Double positionY) {
        this.positionY = positionY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressEntity that = (AddressEntity) o;

        if (addrId != that.addrId) return false;
        if (addressname != null ? !addressname.equals(that.addressname) : that.addressname != null) return false;
        if (positionX != null ? !positionX.equals(that.positionX) : that.positionX != null) return false;
        if (positionY != null ? !positionY.equals(that.positionY) : that.positionY != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = addrId;
        result = 31 * result + (addressname != null ? addressname.hashCode() : 0);
        result = 31 * result + (positionX != null ? positionX.hashCode() : 0);
        result = 31 * result + (positionY != null ? positionY.hashCode() : 0);
        return result;
    }
}
