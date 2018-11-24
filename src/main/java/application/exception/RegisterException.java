package application.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Creator: DreamBoy
 * Date: 2018/11/23.
 */
public class RegisterException extends Exception {
    public RegisterException(String message) {
        super(message);
    }
}
