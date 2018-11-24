package application.repository;

import application.entity.User;
import application.entity.userSecurity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * Creator: DreamBoy
 * Date: 2018/10/31.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback
    public void allStudents() throws Exception {
        User user = new User();
        user.setNickname("txh");
        userRepository.save(user);
        User user2 = new User();
        user2.setNickname("hxt");
        userRepository.save(user2);

        assertEquals(userRepository.allStudents().size(),2 );
    }



    @Test
    // 测试的时候记得加上rollback,数据库不应该留下测试的内容
    @Rollback()
    public void findByEmailAndPassword() throws Exception {
        User user = new User();
        user.setNickname("txh");
        user.setEmail("test@163.com");
        user.setPassword("123456");
        userRepository.save(user);
        User test = userRepository.findByEmailAndPassword("test@163.com", "123456");
        assertEquals(test.getRole(), Role.USER);
    }
}