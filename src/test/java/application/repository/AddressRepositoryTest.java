package application.repository;

import application.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;

    @Test
    @Rollback
    public void findByAddrId() {
        Address address = new Address();
        address.setAddressName("Fudan");
        address.setPositionX(2.0);
        address.setPositionY(1.5);
        addressRepository.save(address);
        int id = address.getAddrId();
        Address test = addressRepository.findByAddrId(id);
        assertEquals(test.getAddressName(), "Fudan");
    }

    @Test
    @Rollback
    public void findByAddressname() {
        Address address = new Address();
        address.setAddressName("Fudan");
        address.setPositionX(2.0);
        address.setPositionY(1.5);
        addressRepository.save(address);

        Address address1 = new Address();
        address1.setAddressName("Shanghai");
        address1.setPositionX(2.0);
        address1.setPositionY(1.5);
        addressRepository.save(address1);

        Address address2 = new Address();
        address2.setAddressName("Shanghai");
        address2.setPositionX(180.0);
        address2.setPositionY(33.5);
        addressRepository.save(address2);

        List<Address> list = addressRepository.findByAddressName("Shanghai");
        Address test1 = list.get(0);
        Address test2 = list.get(1);
        assertEquals(test1.getPositionX(), new Double(2.0));
        assertEquals(test1.getPositionY(), new Double(1.5));
        assertEquals(test2.getPositionX(), new Double(180.0));
        assertEquals(test2.getPositionY(), new Double(33.5));
    }

    @Test
    @Rollback
    public void findByPosition() {
        Address address = new Address();
        address.setAddressName("Shanghai");
        address.setPositionX(2.0);
        address.setPositionY(1.5);
        addressRepository.save(address);

        Address address1 = new Address();
        address1.setAddressName("Fudan");
        address1.setPositionX(2.0);
        address1.setPositionY(1.5);
        addressRepository.save(address1);

        List<Address> list = addressRepository.findByPositionXAndPositionY(2.0, 1.5);
        Address test1 = list.get(0);
        Address test2 = list.get(1);
        assertEquals(test1.getAddressName(), "Shanghai");
        assertEquals(test2.getAddressName(), "Fudan");

    }

    @Test
    @Rollback
    public void deleteById() {
        Address address = new Address();
        address.setAddressName("Shanghai");
        address.setPositionX(2.0);
        address.setPositionY(1.5);
        addressRepository.save(address);
        int id = address.getAddrId();
        addressRepository.deleteByAddrId(id);
        assertEquals(addressRepository.findByAddrId(id), null);

    }

    @Test
    @Rollback
    public void deleteAddress() {
        Address address = new Address();
        address.setAddressName("Shanghai");
        address.setPositionX(2.0);
        address.setPositionY(1.5);
        addressRepository.save(address);
        int id = address.getAddrId();
        addressRepository.delete(address);
        assertEquals(addressRepository.findByAddrId(id), null);
    }

    @Test
    @Rollback
    public void updateAddress() {
        Address address = new Address();
        address.setAddressName("Shanghai");
        address.setPositionX(2.0);
        address.setPositionY(1.5);
        addressRepository.save(address);
        int id = address.getAddrId();
        Address address2 = addressRepository.findByAddrId(id);
        address2.setAddressName("Beijing");
        address2.setPositionX(10.0);
        address2.setPositionY(7.5);
        addressRepository.save(address2);

        Address test = addressRepository.findByAddrId(id);
        assertEquals(test.getAddressName(), "Beijing");
        assertEquals(test.getPositionX(), new Double(10.0));
        assertEquals(test.getPositionY(), new Double(7.5));
    }
}
