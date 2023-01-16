package top.pdev.you.common.enums;

import lombok.Getter;

import java.util.Locale;

/**
 * 缓存键
 * Created in 2022/10/2 12:40
 *
 * @author Peter1303
 */
public enum RedisKey {
    /**
     * 实体
     */
    ENTITY,
    /**
     * 登录令牌
     */
    LOGIN_TOKEN(),

    /**
     * 权限
     */
    PERMISSION(),

    /**
     * 初始化
     */
    INIT("system.init"),

    /**
     * 获取微信访问令牌
     */
    WECHAT_ACCESS_TOKEN;

    @Getter
    private final String tag;

    RedisKey() {
        tag = this.name().toLowerCase(Locale.ROOT);
    }

    RedisKey(String tag) {
        this.tag = tag;
    }
}
