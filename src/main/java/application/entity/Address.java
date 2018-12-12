package application.entity;

import application.entity.forms.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

/**
 * Creator: DreamBoy
 * Date: 2018/12/7.
 */
@Entity
public class Address {
    private int addrId;
    private String addressName;
    @JsonView(View.NearByEvent.class)
    private Double positionX;
    @JsonView(View.NearByEvent.class)
    private Double positionY;
    private String poiId;
    private String addressPosition;

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
    @Column(name = "address_name")
    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
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

    @Basic
    @Column(name = "poi_id")
    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    @Basic
    @Column(name = "address_position")
    public String getAddressPosition() {
        return addressPosition;
    }

    public void setAddressPosition(String addressPosition) {
        this.addressPosition = addressPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (addrId != address.addrId) return false;
        if (addressName != null ? !addressName.equals(address.addressName) : address.addressName != null) return false;
        if (positionX != null ? !positionX.equals(address.positionX) : address.positionX != null) return false;
        if (positionY != null ? !positionY.equals(address.positionY) : address.positionY != null) return false;
        if (poiId != null ? !poiId.equals(address.poiId) : address.poiId != null) return false;
        if (addressPosition != null ? !addressPosition.equals(address.addressPosition) : address.addressPosition != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = addrId;
        result = 31 * result + (addressName != null ? addressName.hashCode() : 0);
        result = 31 * result + (positionX != null ? positionX.hashCode() : 0);
        result = 31 * result + (positionY != null ? positionY.hashCode() : 0);
        result = 31 * result + (poiId != null ? poiId.hashCode() : 0);
        result = 31 * result + (addressPosition != null ? addressPosition.hashCode() : 0);
        return result;
    }
}
