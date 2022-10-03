package top.pdev.you.common.constant;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 缓存键
 * Created in 2022/10/2 12:40
 *
 * @author Peter1303
 */
public class RedisKey {
    private RedisKey() {
    }

    private static String md5(String input) {
        if (!Optional.ofNullable(input).isPresent()) {
            throw new NullPointerException("redis key null");
        }
        return DigestUtils.md5DigestAsHex(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 登录令牌
     *
     * @param token 令牌
     * @return {@link String}
     */
    public static String loginToken(String token) {
        return "token." + md5(token);
    }

    /**
     * 权限
     *
     * @param uidOrToken uid或令牌
     * @return {@link String}
     */
    public static String permission(Object uidOrToken) {
        return "permission." + md5(uidOrToken.toString());
    }

    /**
     * 初始化
     *
     * @return {@link String}
     */
    public static String init() {
        return "system.init";
    }
}
