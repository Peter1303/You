package top.pdev.you.common.exception;

import lombok.Getter;

/**
 * 内部错误
 * Created in 2022/8/14 19:27
 *
 * @author Peter1303
 */
public class InternalErrorException extends RuntimeException {
    @Getter
    private String message;

    @Getter
    private Throwable cause;

    public InternalErrorException() {
    }

    public InternalErrorException(String message) {
        this.message = message;
    }

    public InternalErrorException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }
}
