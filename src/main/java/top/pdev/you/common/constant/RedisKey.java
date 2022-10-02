package top.pdev.you.common.constant;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 缓存键
 * Created in 2022/10/2 12:40
 *
 * @author Peter1303
 */
public class RedisKey {
    private RedisKey() {
    }

    /**
     * 登录令牌
     *
     * @param token 令牌
     * @return {@link String}
     */
    public static String loginToken(String token) {
        return "token." + DigestUtils.md5DigestAsHex(token.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 权限
     *
     * @param uidOrToken uid或令牌
     * @return {@link String}
     */
    public static String permission(Object uidOrToken) {
        return "permission." + DigestUtils.md5DigestAsHex(uidOrToken.toString()
                .getBytes(StandardCharsets.UTF_8));
    }
}
