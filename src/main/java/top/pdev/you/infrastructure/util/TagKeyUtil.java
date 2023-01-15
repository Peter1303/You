package top.pdev.you.infrastructure.util;

import cn.hutool.crypto.SecureUtil;
import top.pdev.you.common.enums.RedisKey;

import java.util.Optional;

/**
 * 标签键工具类
 * Created in 2023/1/15 12:37
 *
 * @author Peter1303
 */
public class TagKeyUtil {
    private TagKeyUtil() {
    }

    public static String get(RedisKey redisKey, Object value) {
        if (value instanceof String) {
            value = SecureUtil.md5((String) value);
        }
        if (!Optional.ofNullable(value).isPresent()) {
            value = "";
        }
        return redisKey.getTag() + "." + value;
    }

    public static String get(RedisKey redisKey) {
        return get(redisKey, null);
    }
}
