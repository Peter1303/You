package top.pdev.you.infrastructure.result;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import top.pdev.you.common.constant.Constants;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.infrastructure.util.SpringEnvHelper;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 结果
 * Created in 2022/10/1 18:10
 *
 * @author Peter1303
 */
@Data
public class Result<E> implements Serializable {
    private static final String DEV = "dev";
    /**
     * 结果代码
     */
    private Integer code;
    /**
     * 统一返回结果
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    /**
     * 消息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("msg")
    private String message = null;

    /**
     * 私有构造器
     */
    private Result() {
    }

    /**
     * 默认没有 data 的结果
     * <p>Example: {"code":200}</p>
     *
     * @param code 代码
     */
    public Result(ResultCode code) {
        this.code = code.getCode();
        // 错误代码有消息
        if (StrUtil.isNotBlank(code.getMessage())) {
            this.message = code.getMessage();
        }
    }

    /**
     * 带有数据的结果
     *
     * @param code 代码
     * @param data 数据
     */
    public Result(ResultCode code, Object data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    /**
     * 默认的返回结果
     *
     * @return 结果
     */
    public static <E> Result<E> ok() {
        return new Result<>(ResultCode.OK);
    }

    /**
     * 返回带有数据的成功结果
     *
     * @param data 数据
     * @return 结果
     */
    public static <E> Result<E> ok(Object data) {
        return new Result<>(ResultCode.OK, data);
    }

    /**
     * 失败结果
     *
     * @param code 失败代码
     * @return 结果
     */
    public static <E> Result<E> failed(ResultCode code) {
        return new Result<>(code);
    }

    /**
     * 默认失败结果
     *
     * @return 结果
     */
    public static <E> Result<E> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 默认的系统错误结果
     *
     * @return 结果
     */
    public static <E> Result<E> error() {
        return new Result<>(ResultCode.ERROR);
    }

    /**
     * 系统默认带有错误结果
     *
     * @param ex 异常
     * @return 结果
     */
    public static <E> Result<E> error(Exception ex) {
        return error(ResultCode.ERROR, ex);
    }

    /**
     * 系统错误结果
     *
     * @param ex 异常
     * @return 结果
     */
    public static <E> Result<E> error(ResultCode code, Exception ex) {
        Result<E> result = new Result<>(code);
        // 开发环境可以看到日志
        if (SpringEnvHelper.isEnv(DEV)) {
            result.put(Constants.ERROR, ex);
        }
        return result;
    }

    /**
     * 带有数据的错误返回结果
     *
     * @param code 代码
     * @return 结果
     */
    public static <E> Result<E> error(ResultCode code) {
        return new Result<>(code);
    }

    /**
     * 设置结果数据
     *
     * @param object 数据
     * @return 结果
     */
    public Result<E> setData(Object object) {
        this.data = object;
        return this;
    }

    /**
     * 设置消息
     *
     * @param message 消息
     * @return 结果
     */
    public Result<E> setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * 添加数据到结果中
     *
     * @param tag   标签
     * @param value 值
     * @return 实例
     */
    @SuppressWarnings("unchecked")
    public Result<E> put(String tag, Object value) {
        if (data == null) {
            data = new HashMap<>(1);
        }
        if (data instanceof HashMap) {
            ((HashMap<String, Object>) data).put(tag, value);
        } else {
            throw new InternalErrorException("已经有 VO 数据");
        }
        return this;
    }
}
