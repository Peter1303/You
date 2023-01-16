package top.pdev.you.common.provider;

import lombok.RequiredArgsConstructor;
import top.pdev.you.common.interfaces.OnCache;
import top.pdev.you.infrastructure.redis.RedisService;
import top.pdev.you.infrastructure.util.TagKeyUtil;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 缓存处理器
 * Created in 2023/1/15 21:38
 *
 * @author Peter1303
 */
@RequiredArgsConstructor
public class CacheHandler {
    private final RedisService redisService;

    /**
     * 缓存接口
     */
    private OnCache onCache;

    private long sec = 10;

    private Class<?> type;

    private String method;

    private Object[] params;

    public CacheHandler sec(long sec) {
        this.sec = sec;
        return this;
    }

    public CacheHandler type(Class<?> type) {
        this.type = type;
        return this;
    }

    public CacheHandler method(String method) {
        this.method = method;
        return this;
    }

    public CacheHandler params(Object[] params) {
        this.params = params;
        return this;
    }

    public CacheHandler provider(OnCache onCache) {
        this.onCache = onCache;
        return this;
    }

    public Object cache() throws Throwable {
        String tag = TagKeyUtil.get(type, method, params);
        if (redisService.hasKey(tag)) {
            return redisService.getObject(tag, type);
        }
        Object o = onCache.get();
        if (Optional.ofNullable(o).isPresent()) {
            redisService.set(tag, o, sec, TimeUnit.SECONDS);
        }
        return o;
    }

    public void remove() {
        String tag = TagKeyUtil.get(type);
        redisService.deleteContaining("*" + tag + "*");
    }
}
