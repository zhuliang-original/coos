package top.coos.exception;

/**
 * 核心异常处理
 * 
 * @author 朱亮
 * 
 */
public class CoreException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Error error;

    public CoreException(Exception exception) {
        super(exception);
        this.error = Error.BASE;
    }

    public CoreException(String message) {
        super(message);
        this.error = Error.BASE;
    }

    public CoreException(Error error) {
        super(error.getErrmsg());
        this.error = error;
    }

    public CoreException(Error error, String message) {
        super(message);
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
