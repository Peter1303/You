package top.pdev.you.infrastructure.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.DigestUtils;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.validator.TokenValidator;
import top.pdev.you.infrastructure.result.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * 令牌工具类
 * Created in 2022/8/15 16:08
 *
 * @author Peter1303
 */
public class TokenUtil {
    private TokenUtil() {
    }

    /**
     * 生成令牌
     *
     * @return {@link String}
     */
    public static String generateToken() {
        UUID uuid = UUID.randomUUID();
        String string = uuid.toString();
        return DigestUtils.md5DigestAsHex(string.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 通过 header 得到令牌
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getTokenByHeader(HttpServletRequest request) {
        String token = request.getHeader("token");
        boolean valid = new TokenValidator().isValid(token, null);
        if (!valid || StrUtil.isBlankIfStr(token)) {
            throw new BusinessException(ResultCode.PERMISSION_DENIED);
        }
        return token;
    }
}
