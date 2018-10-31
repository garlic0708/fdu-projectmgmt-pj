package application.repository;

import application.entity.User;
import org.springframework.data.repository.CrudRepository;

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
     * 通过email和password返回用户
     * @param email
     * @param password
     * @return
     */
    User findByEmailAndPassword(String email, String password);
}
