package top.pdev.you.domain.repository.base;

import org.springframework.stereotype.Repository;
import top.pdev.you.domain.repository.base.interfaces.CacheRepository;
import top.pdev.you.infrastructure.redis.RedisService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 缓存仓库实现类
 * Created in 2023/1/4 15:58
 *
 * @author Peter1303
 */
@Repository
public abstract class CacheRepositoryImpl<E> implements CacheRepository<E> {
    @Resource
    private RedisService redisService;

    @Override
    public void save(String key, E entity) {
        save(key, entity, 0, null);
    }

    @Override
    public void save(String key, E entity, long timeout, TimeUnit unit) {
        redisService.set(key, entity, timeout, unit);
    }

    @Override
    public <T> T find(String key, Class<T> clazz) {
        return redisService.getObject(key, clazz);
    }

    @Override
    public Object find(String key) {
        return redisService.get(key);
    }

    @Override
    public void delete(String key) {
        redisService.delete(key);
    }

    @Override
    public Boolean exists(String key) {
        return redisService.hasKey(key);
    }
}
