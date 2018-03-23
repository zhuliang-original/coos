package top.coos.exception;

public class BasepmRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BasepmRuntimeException() {
        super();
    }

    public BasepmRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasepmRuntimeException(String message) {
        super(message);
    }

    public BasepmRuntimeException(Throwable cause) {
        super(cause);
    }

}
