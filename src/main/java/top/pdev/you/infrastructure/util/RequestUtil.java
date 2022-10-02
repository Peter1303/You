package top.pdev.you.infrastructure.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 请求工具类
 * Created in 2022/8/19 22:41
 *
 * @author Peter1303
 */
public class RequestUtil {
    /**
     * get请求
     *
     * @return {@link HttpServletRequest}
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        Optional.ofNullable(attributes).orElseThrow(NullPointerException::new);
        return attributes.getRequest();
    }
}
