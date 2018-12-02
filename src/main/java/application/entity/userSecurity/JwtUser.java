package application.entity.userSecurity;

import application.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;


/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
public class JwtUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public JwtUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }


    public User getUser() {
        return user;
    }

    public Long getId() {
        return (long) user.getuId();
    }

    public Role getRole() {
        return user.getRole();
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
