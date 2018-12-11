package application.repository;

import application.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Optional;

/**
 * Creator: DreamBoy
 * Date: 2018/10/31.
 */

public interface UserRepository extends CrudRepository<User, Long> {
    User getByUId(int uid);

    /**
     * 通过uid返回用户
     * @param uid
     * @return
     */
    User findByUId(int uid);

    /**
     *
     * @param nickname
     * @return
     */
    User findByNickname(String nickname);

    /**
     *
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);

    User getByEmail(String email);

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
