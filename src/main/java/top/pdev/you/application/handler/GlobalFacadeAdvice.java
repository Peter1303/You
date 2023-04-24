package top.pdev.you.application.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.infrastructure.result.ResultCode;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * 全局 Facade 处理
 * Created in 2022/8/14 16:25
 *
 * @author Peter1303
 */
@Slf4j
@RestControllerAdvice
public class GlobalFacadeAdvice {
    /**
     * 捕获业务失败
     *
     * @param ex 异常
     * @return 结果
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result<?> handleException(BusinessException ex) {
        ResultCode code = ex.getCode();
        Result<Object> result = Result.failed(code);
        String message = ex.getMessage();
        if (Optional.ofNullable(message).isPresent()) {
            result.setMessage(message);
        }
        return result;
    }

    /**
     * 捕获参数错误
     *
     * @param ex 异常
     * @return 结果
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Result<?> handleException(MissingServletRequestParameterException ex) {
        return Result.error(ResultCode.PARAM_MISSING, ex);
    }

    /**
     * 捕获参数错误
     *
     * @param ex 异常
     * @return 结果
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result<?> handleException(BindException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        HashMap<String, String> errors = new HashMap<>(16);
        StringJoiner joiner = new StringJoiner("\n");
        fieldErrors.forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
            joiner.add(error.getDefaultMessage());
        });
        return Result.failed(ResultCode.PARAM_MISSING)
                .setData(errors)
                .setMessage(joiner.toString());
    }

    /**
     * 404 错误处理
     *
     * @param ex 异常
     * @return 结果
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Result<?> handleException(NoHandlerFoundException ex) {
        return Result.failed(ResultCode.NOT_FOUND);
    }

    /**
     * 404 错误处理
     *
     * @param ex 异常
     * @return 结果
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result<?> handleException(HttpRequestMethodNotSupportedException ex) {
        return Result.failed(ResultCode.NOT_FOUND);
    }

    /**
     * 捕获参数转换异常
     *
     * @param ex 异常
     * @return 结果
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result<?> handleException(HttpMessageNotReadableException ex) {
        return Result.error(ResultCode.PARAM_MISSING);
    }

    /**
     * 捕获所有的异常
     *
     * @param ex 异常
     * @return 结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> handleException(Exception ex) {
        // 记录数据库问题
        if (ex instanceof NestedRuntimeException) {
            log.error("数据库问题");
        } else {
            log.error(ex.getMessage(), ex);
        }
        ex.printStackTrace();
        return Result.error(ex);
    }
}
