package top.pdev.you.common.exception;

import lombok.Getter;
import top.pdev.you.infrastructure.result.ResultCode;

/**
 * 业务异常
 * Created in 2022/8/15 21:33
 *
 * @author Peter1303
 */
public class PermissionDeniedException extends RuntimeException {
    @Getter
    private String message;

    @Getter
    private final ResultCode code = ResultCode.PERMISSION_DENIED;

    @Getter
    private Throwable cause;

    public PermissionDeniedException() {
    }

    public PermissionDeniedException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }
}
