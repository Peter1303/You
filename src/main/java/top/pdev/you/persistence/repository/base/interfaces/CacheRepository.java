package top.pdev.you.persistence.repository.base.interfaces;

import java.util.concurrent.TimeUnit;

/**
 * 缓存仓库基类
 * Created in 2023/1/4 15:54
 *
 * @author Peter1303
 */
public interface CacheRepository<E> {
    /**
     * 保存
     *
     * @param key    键
     * @param entity 实体
     */
    void save(String key, E entity);

    /**
     * 保存
     *
     * @param key     键
     * @param entity  实体
     * @param timeout 超时
     * @param unit    单位
     */
    void save(String key, E entity, long timeout, TimeUnit unit);

    /**
     * 获取
     *
     * @param key   键
     * @param clazz 类
     * @return {@link T}
     */
    <T> T find(String key, Class<T> clazz);

    /**
     * 获取
     *
     * @param key 键
     * @return {@link Object}
     */
    Object find(String key);

    /**
     * 删除
     *
     * @param key 键
     */
    void delete(String key);

    /**
     * 存在
     *
     * @param key 键
     * @return {@link Boolean}
     */
    Boolean exists(String key);
}
