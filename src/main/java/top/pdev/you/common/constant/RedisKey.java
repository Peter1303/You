package top.pdev.you.common.constant;

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
        return "token." + token;
    }
}
