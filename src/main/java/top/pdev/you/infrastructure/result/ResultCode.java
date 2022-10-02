package top.pdev.you.infrastructure.result;

import lombok.Getter;

/**
 * 代码
 * Created in 2022/10/1 18:11
 *
 * @author Peter1303
 */
@Getter
public enum ResultCode {
    /**
     * 默认失败
     */
    FAILED(0, "无法完成请求"),
    /**
     * 参数缺失
     */
    PARAM_MISSING(100, "参数错误"),
    /**
     * 成功
     */
    OK(200, "成功"),
    /**
     * 未注册
     */
    NOT_REGISTERED(300, "未注册"),
    /**
     * 令牌鉴权失败
     */
    TOKEN_ERROR(400, "Token 鉴权失败"),
    /**
     * 没有找到
     */
    NOT_FOUND(404, "不存在的"),
    /**
     * 错误
     */
    ERROR(500, "系统错误"),
    /**
     * 没有权限
     */
    PERMISSION_DENIED(700, "权限不足");

    private final int code;
    private final String message;

    ResultCode(int code) {
        this.code = code;
        message = "";
    }

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
