package application.repository;

import application.entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface AddressRepository extends CrudRepository<Address,Long> {

    Address findByAddrId(int addrId);

    Address findByAddressname(String addressname);

    Address findByPositionXAndPositionY(Double positionX, Double positionY);

    void deleteByAddrId(int id);

    @Override
    void delete(Address address);

}
