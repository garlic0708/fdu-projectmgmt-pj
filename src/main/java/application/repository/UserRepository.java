package application.repository;

import application.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/10/31.
 */

public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * 通过uid返回用户
     * @param uid
     * @return
     */
    User findByUId(int uid);

    /**
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     *
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 通过email和password返回用户
     * @param email
     * @param password
     * @return
     */
    User findByEmailAndPassword(String email, String password);

    @Query(value = "select * from user", nativeQuery = true)
    List<User> allStudents();

}
