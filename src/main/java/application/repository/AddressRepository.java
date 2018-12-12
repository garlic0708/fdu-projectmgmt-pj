package application.repository;

import application.entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AddressRepository extends CrudRepository<Address, Long> {

    /**
     * 通过ID返回地址
     *
     * @param addrId
     * @return
     */
    Address findByAddrId(int addrId);

    /**
     * 通过poiId返回Address
     * @param poiId
     * @return
     */
    Address findByPoiId(String poiId);

    /**
     * 通过地址名称返回地址列表
     *
     * @param addressName
     * @return
     */
    List<Address> findByAddressName(String addressName);

    /**
     * 通过坐标返回地址列表
     *
     * @param positionX
     * @param positionY
     * @return
     */
    List<Address> findByPositionXAndPositionY(Double positionX, Double positionY);

    /**
     * 根据ID删除地址
     *
     * @param id
     */
    void deleteByAddrId(int id);

    /**
     * 删除地址
     *
     * @param address
     */
    @Override
    void delete(Address address);

    @Query(value = "SELECT * FROM address WHERE address_name = ? AND positionX = ? AND positionY = ?", nativeQuery = true)
    Address findByAddressNameAndPositionXAndPositionY(String addressName, double positionX, double positionY);

}
