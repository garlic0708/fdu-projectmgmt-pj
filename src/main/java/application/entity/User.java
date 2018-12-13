package application.entity;

import application.entity.forms.View;
import application.entity.userSecurity.Role;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
@Entity
public class User {
    @JsonView(View.UserView.class)
    private int uId;
    @JsonView(View.UserView.class)
    private String email;
    private String password;
    @JsonView(View.UserView.class)
    private String nickname;
    @JsonView(View.UserView.class)
    private Integer credit;
    private String image;
    private Role role;

    public User() {
        this.role = Role.USER;
    }

    @Id
    @GeneratedValue
    @Column(name = "u_id")
    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String username) {
        this.nickname = username;
    }

    @Basic
    @Column(name = "credit")
    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer vredict) {
        this.credit = vredict;
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "role")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (uId != user.uId) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null) return false;
        if (credit != null ? !credit.equals(user.credit) : user.credit != null) return false;
        if (image != null ? !image.equals(user.image) : user.image != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uId;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (credit != null ? credit.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
