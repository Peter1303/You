package top.pdev.you.infrastructure.redis;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.pdev.you.infrastructure.config.CacheProperties;
import top.pdev.you.infrastructure.util.JacksonUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存服务
 * Created in 2022/5/1 13:15
 *
 * @author Peter1303
 */
@Slf4j
@Service
public class RedisService {
    @Resource
    private CacheProperties cacheProperties;

    private final StringRedisTemplate stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);

    /**
     * 数据缓存至 redis
     *
     * @param key   键
     * @param value 值
     */
    public <K, V> void set(K key, V value) {
        set(key, value, -1, null);
    }

    /**
     * 数据缓存至 redis 并设置过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期
     * @param unit    过期单位
     */
    public <K, V> void set(K key, V value, long timeout, TimeUnit unit) {
        try {
            if (value != null) {
                stringRedisTemplate.opsForValue().set(cacheProperties.getPrefix() + key,
                        JacksonUtil.getInstance().writeValueAsString(value));
                if (unit != null) {
                    stringRedisTemplate.expire(cacheProperties.getPrefix() + key, timeout, unit);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("数据缓存至 redis 失败");
        }
    }

    /**
     * 写入 hash-set
     * <p>已经是 key-value 的键值 不能再写入为 hash-set</p>
     *
     * @param key    不可为 {@literal null}.
     * @param subKey 不可为 {@literal null}.
     * @param value  写入的值
     */
    public <K, SK, V> void setHashCache(K key, SK subKey, V value) {
        stringRedisTemplate.opsForHash().put(cacheProperties.getPrefix() + key, subKey, value);
    }

    /**
     * 写入 hash-set 并设置过期时间
     *
     * @param key     不可为 {@literal null}.
     * @param subKey  不可为 {@literal null}.
     * @param value   写入的值
     * @param timeout 过期
     * @param unit    过期单位
     */
    public <K, SK, V> void setHashCache(K key, SK subKey, V value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForHash().put(cacheProperties.getPrefix() + key, subKey, value);
        stringRedisTemplate.expire(cacheProperties.getPrefix() + key, timeout, unit);
    }

    /**
     * 获取 hash-setValue
     *
     * @param key    不可为 {@literal null}.
     * @param subKey 不可为 {@literal null}.
     */
    public <K, SK> Object getHashCache(K key, SK subKey) {
        return stringRedisTemplate.opsForHash().get(cacheProperties.getPrefix() + key, subKey);
    }


    /**
     * 从 redis 中获取缓存数据转成对象
     *
     * @param key   不可为 {@literal null}.
     * @param clazz 对象类型
     * @return 对象
     */
    public <K, V> V getObject(K key, Class<V> clazz) {
        String value = this.get(key);
        V result = null;
        if (!StrUtil.isEmpty(value)) {
            try {
                result = JacksonUtil.getInstance().readValue(value, clazz);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 从 redis 中获取缓存数据 转成 list
     *
     * @param key 不可为 {@literal null}.
     * @return list
     */
    public <K, V> List<V> getList(K key) {
        String value = this.get(key);
        List<V> result = Collections.emptyList();
        if (!StrUtil.isEmpty(value)) {
            try {
                result = JacksonUtil.getInstance()
                        .readValue(value, new TypeReference<List<V>>() {
                        });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取 {@code key}
     *
     * @param key 不可为 {@literal null}
     * @return java.lang.String
     **/
    public <K> String get(K key) {
        String value;
        try {
            value = stringRedisTemplate.opsForValue().get(cacheProperties.getPrefix() + key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("从 redis 缓存中获取缓存数据失败");
        }
        return value;
    }

    /**
     * 删除 key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(cacheProperties.getPrefix() + key);
    }

    /**
     * 批量删除 key
     */
    public void delete(Collection<String> keys) {
        Collection<String> temp = new ArrayList<>();
        keys.forEach(key -> temp.add(cacheProperties.getPrefix() + temp));
        stringRedisTemplate.delete(keys);
    }

    /**
     * 序列化 key
     */
    public byte[] dump(String key) {
        return stringRedisTemplate.dump(key);
    }

    /**
     * 是否存在 key
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(cacheProperties.getPrefix() + key);
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置过期时间
     */
    public Boolean expireAt(String key, DateTime dateTime) {
        return stringRedisTemplate.expireAt(key, dateTime.toJdkDate());
    }


    /**
     * 移除 key 的过期时间，key 将持久保持
     */
    public Boolean persist(String key) {
        return stringRedisTemplate.persist(key);
    }

    /**
     * 返回 key 的剩余的过期时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        return stringRedisTemplate.getExpire(key, unit);
    }

    /**
     * 返回 key 的剩余的过期时间
     */
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }
}
