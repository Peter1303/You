package top.pdev.you.common.exception;

import top.pdev.you.infrastructure.result.ResultCode;

/**
 * 令牌错误异常
 * Created in 2022/10/19 10:17
 *
 * @author Peter1303
 */
public class TokenInvalidException extends BusinessException {
    public TokenInvalidException() {
        super(ResultCode.PARAM_MISSING, "令牌错误");
    }
}
