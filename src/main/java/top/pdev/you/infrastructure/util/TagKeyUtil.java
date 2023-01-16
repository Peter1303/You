package top.pdev.you.infrastructure.util;

import cn.hutool.crypto.SecureUtil;
import top.pdev.you.common.enums.RedisKey;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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

    public static String get(Class<?> clazz, String method, Object value) {
        String className = clazz.getName();
        StringJoiner joiner = new StringJoiner(".");
        joiner.add(RedisKey.ENTITY.getTag())
                .add(className);
        if (Optional.ofNullable(method).isPresent()) {
            joiner.add(method);
        }
        if (Optional.ofNullable(value).isPresent()) {
            joiner.add(SecureUtil.md5(String.valueOf(value)));
        }
        return joiner.toString();
    }

    public static String get(Class<?> clazz, String method, Object[] params) {
        String value = Optional.ofNullable(params).isPresent()
                ? Arrays.stream(params)
                .map(String::valueOf)
                .collect(Collectors.joining(","))
                : null;
        return get(clazz, method, value);
    }

    public static String get(Class<?> clazz) {
        return get(clazz, null, null);
    }

    public static String get(RedisKey redisKey) {
        return get(redisKey, null);
    }
}
