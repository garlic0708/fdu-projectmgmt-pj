package application.repository;

import application.SpringBootWebApplication;

import application.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Creator: DreamBoy
 * Date: 2018/10/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {SpringBootWebApplication.class})
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback()
    public void findByUId() throws Exception {
        User user = new User();
        user.setUsername("txh");
        user = userRepository.save(user);
        User test = userRepository.findByUId(user.getuId());
        assertEquals(test.getUsername(), "txh");
    }

}