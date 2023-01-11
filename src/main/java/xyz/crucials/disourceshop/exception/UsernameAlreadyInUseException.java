package xyz.crucials.disourceshop.exception;

import org.springframework.security.core.AuthenticationException;

public class UsernameAlreadyInUseException extends AuthenticationException {

    public UsernameAlreadyInUseException(String username) {
        super("Username " + username + " is already in use");
    }

}
