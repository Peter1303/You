package top.pdev.you.common.exception;

import lombok.Getter;
import top.pdev.you.infrastructure.result.ResultCode;

/**
 * 业务异常
 * Created in 2022/8/15 21:33
 *
 * @author Peter1303
 */
public class PermissionDeniedException extends BusinessException {
    @Getter
    private final String message = "权限不足";

    @Getter
    private final ResultCode code = ResultCode.PERMISSION_DENIED;

    public PermissionDeniedException() {
        super(ResultCode.PERMISSION_DENIED);
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(ResultCode.PERMISSION_DENIED, message, cause);
    }
}
