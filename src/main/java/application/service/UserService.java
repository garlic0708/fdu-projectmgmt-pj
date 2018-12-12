package application.service;

import application.entity.User;
import application.entity.forms.UserCheckIn;
import application.exception.JoinEventException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
public interface UserService {
    List<User> getAllUsers();

    Optional<User> getByEmail(String email);

    void joinEvent(int uid, int eid) throws JoinEventException;

    /**
     * 通过event的eid，返回这个event的参与情况，要构造的数据为UserCheckIn，具体要求见entity.forms.UserCheckIn
     * 另外，这个list中不需包含创建者，即如果是INITIATOR，则忽略
     * @param eid
     * @return
     */
    List<UserCheckIn> getUserCheckIn(int eid);
}
