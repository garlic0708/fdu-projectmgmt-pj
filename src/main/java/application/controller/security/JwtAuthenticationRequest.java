package application.controller.security;

import java.io.Serializable;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
public class JwtAuthenticationRequest implements Serializable {


    private static final long serialVersionUID = -8445943548965154778L;

    private String email;
    private String password;


    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
