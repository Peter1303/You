package top.pdev.you.common.exception;

import lombok.Getter;
import top.pdev.you.infrastructure.result.ResultCode;

/**
 * 业务异常
 * Created in 2022/8/15 21:33
 *
 * @author Peter1303
 */
@Getter
public class BusinessException extends RuntimeException {
    private String message;
    private final ResultCode code;
    private Throwable cause;

    public BusinessException() {
        code = ResultCode.FAILED;
    }

    public BusinessException(ResultCode code) {
        this.code = code;
    }

    public BusinessException(ResultCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        code = ResultCode.FAILED;
        this.message = message;
    }

    public BusinessException(ResultCode code, String message, Throwable cause) {
        this.code = code;
        this.message = message;
        this.cause = cause;
    }
}
