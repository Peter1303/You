package top.pdev.you.infrastructure.redis;

import cn.hutool.core.date.DateTime;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存服务
 * Created in 2022/5/1 13:15
 *
 * @author Peter1303
 */
public interface RedisService {
    /**
     * 数据缓存至 redis
     *
     * @param key   键
     * @param value 值
     */
    <K, V> void set(K key, V value);

    /**
     * 数据缓存至 redis 并设置过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期
     * @param unit    过期单位
     */
    <K, V> void set(K key, V value, long timeout, TimeUnit unit);

    /**
     * 写入 hash-set
     * <p>已经是 key-value 的键值 不能再写入为 hash-set</p>
     *
     * @param key    不可为 {@literal null}.
     * @param subKey 不可为 {@literal null}.
     * @param value  写入的值
     */
    <K, SK, V> void setHashCache(K key, SK subKey, V value);

    /**
     * 写入 hash-set 并设置过期时间
     *
     * @param key     不可为 {@literal null}.
     * @param subKey  不可为 {@literal null}.
     * @param value   写入的值
     * @param timeout 过期
     * @param unit    过期单位
     */
    <K, SK, V> void setHashCache(K key, SK subKey, V value, long timeout, TimeUnit unit);

    /**
     * 获取 hash-setValue
     *
     * @param key    不可为 {@literal null}.
     * @param subKey 不可为 {@literal null}.
     */
    <K, SK> Object getHashCache(K key, SK subKey);

    /**
     * 从 redis 中获取缓存数据转成对象
     *
     * @param key   不可为 {@literal null}.
     * @param clazz 对象类型
     * @return 对象
     */
    <K, V> V getObject(K key, Class<V> clazz);

    /**
     * 从 redis 中获取缓存数据 转成 list
     *
     * @param key 不可为 {@literal null}.
     * @return list
     */
    <K, V> List<V> getList(K key);

    /**
     * 获取 {@code key}
     *
     * @param key 不可为 {@literal null}
     * @return java.lang.String
     **/
    <K> String get(K key);

    /**
     * 键
     *
     * @param pattern 模糊
     * @return {@link Set}<{@link String}>
     */
    Set<String> keys(String pattern);

    /**
     * 删除包含
     *
     * @param patten 彭定康
     */
    void deleteContaining(String patten);

    /**
     * 删除 key
     */
    void delete(String key);

    /**
     * 批量删除 key
     */
    void delete(Collection<String> keys);

    /**
     * 序列化 key
     */
    byte[] dump(String key);

    /**
     * 是否存在 key
     */
    Boolean hasKey(String key);

    /**
     * 设置过期时间
     */
    Boolean expire(String key, long timeout, TimeUnit unit);

    /**
     * 设置过期时间
     */
    Boolean expireAt(String key, DateTime dateTime);

    /**
     * 移除 key 的过期时间，key 将持久保持
     */
    Boolean persist(String key);

    /**
     * 返回 key 的剩余的过期时间
     */
    Long getExpire(String key, TimeUnit unit);

    /**
     * 返回 key 的剩余的过期时间
     */
    Long getExpire(String key);
}
