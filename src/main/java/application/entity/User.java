package application.entity;

import application.entity.userSecurity.Role;

import javax.persistence.*;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
@Entity
public class User {
    private int uId;
    private String email;
    private String password;
    private String nickname;
    private Integer vredict;
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
    @Column(name = "vredict")
    public Integer getVredict() {
        return vredict;
    }

    public void setVredict(Integer vredict) {
        this.vredict = vredict;
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
        if (vredict != null ? !vredict.equals(user.vredict) : user.vredict != null) return false;
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
        result = 31 * result + (vredict != null ? vredict.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
