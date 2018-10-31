package application.entity;

import javax.persistence.*;

/**
 * Creator: DreamBoy
 * Date: 2018/10/31.
 */
@Entity
public class Address {
    private int addrId;
    private String addressname;
    private Double positionX;
    private Double positionY;

    @Id
    @GeneratedValue
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

        Address address = (Address) o;

        if (addrId != address.addrId) return false;
        if (addressname != null ? !addressname.equals(address.addressname) : address.addressname != null) return false;
        if (positionX != null ? !positionX.equals(address.positionX) : address.positionX != null) return false;
        if (positionY != null ? !positionY.equals(address.positionY) : address.positionY != null) return false;

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
