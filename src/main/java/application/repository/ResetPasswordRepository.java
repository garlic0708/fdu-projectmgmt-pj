package application.repository;

import application.entity.userSecurity.PasswordReset;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/12/14.
 */
public interface ResetPasswordRepository extends CrudRepository<PasswordReset, Long> {
    PasswordReset findByPrId(long id);

    PasswordReset findByUid(int uid);
}
