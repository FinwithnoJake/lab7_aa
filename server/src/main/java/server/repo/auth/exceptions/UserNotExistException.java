package server.repo.auth.exceptions;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException (String message) {
        super(message);
    }
}
