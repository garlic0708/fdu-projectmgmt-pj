package application.entity.userSecurity;

import javax.persistence.*;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * Creator: DreamBoy
 * Date: 2018/11/24.
 */
@Entity
@Table(name = "verification_token", schema = "yueya")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    private int vId;
    private String token;
    private String email;
    private String password;
    private String nickname;
    private Role role;
    private Integer vredict;
    private String image;
    private Date expiryDate = calculateExpiryDate();

    @Id
    @Column(name = "v_id")
    public int getvId() {
        return vId;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "role")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
    @Column(name = "expiryDate")
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VerificationToken that = (VerificationToken) o;

        if (vId != that.vId) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (vredict != null ? !vredict.equals(that.vredict) : that.vredict != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (expiryDate != null ? !expiryDate.equals(that.expiryDate) : that.expiryDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vId;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (vredict != null ? vredict.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        return result;
    }

    private java.util.Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(java.util.Date.from(Instant.now()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new java.util.Date(cal.getTime().getTime());
    }
}
