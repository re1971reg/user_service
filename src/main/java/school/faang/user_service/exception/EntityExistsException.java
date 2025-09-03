package school.faang.user_service.exception;

public class EntityExistsException extends RuntimeException {

    public EntityExistsException(String message) {
        super(message);
    }
}
