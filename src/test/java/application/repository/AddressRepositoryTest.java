package application.repository;

import application.SpringBootWebApplication;

import application.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

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
        address.setAddressname("Fudan");
        address.setPositionX(2.0);
        address.setPositionY(1.5);
        addressRepository.save(address);
        int id = address.getAddrId();
        Address test = addressRepository.findByAddrId(id);
        assertEquals(test.getAddressname(), "Fudan");
    }

    @Test
    @Rollback
    public void findByAddressname() {
        Address address = new Address();
        address.setAddressname("Shanghai");
        address.setPositionX(2.0);
        address.setPositionY(1.5);
        addressRepository.save(address);
        Address test = addressRepository.findByAddressname("Shanghai");
        assertEquals(test.getPositionX(), new Double(2.0));
        assertEquals(test.getPositionY(), new Double(1.5));
    }

    @Test
    @Rollback
    public void findByPosition() {
        Address address = new Address();
        address.setAddressname("Shanghai");
        address.setPositionX(2.0);
        address.setPositionY(1.5);
        addressRepository.save(address);
        Address test = addressRepository.findByPositionXAndPositionY(2.0, 1.5);
        assertEquals(test.getAddressname(), "Shanghai");
    }

    @Test
    @Rollback
    public void deleteById() {
        Address address = new Address();
        address.setAddressname("Shanghai");
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
        address.setAddressname("Shanghai");
        address.setPositionX(2.0);
        address.setPositionY(1.5);
        addressRepository.save(address);
        addressRepository.delete(address);
        assertEquals(addressRepository.findByAddrId(address.getAddrId()), null);
    }

    @Test
    @Rollback
    public void updateAddress() {
        Address address = new Address();
        address.setAddressname("Shanghai");
        address.setPositionX(2.0);
        address.setPositionY(1.5);
        addressRepository.save(address);
        int id = address.getAddrId();
        Address address2 = addressRepository.findByAddrId(id);
        address2.setAddressname("Beijing");
        address2.setPositionX(10.0);
        address2.setPositionY(7.5);
        addressRepository.save(address2);

        Address test = addressRepository.findByAddrId(id);
        assertEquals(test.getAddressname(), "Beijing");
        assertEquals(test.getPositionX(), new Double(10.0));
        assertEquals(test.getPositionY(), new Double(7.5));
    }
}
