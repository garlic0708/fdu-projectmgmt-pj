package application.repository;

import application.entity.Address;
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
     * 通过地址名称返回地址列表
     *
     * @param addressname
     * @return
     */
    List<Address> findByAddressname(String addressname);

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

}
