package application.controller.security;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
public class JwtAuthenticationResponse {
    private static final long serialVersionUID = 4784951536404964122L;
    private final String token;   //要发送回客户端的令牌

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
