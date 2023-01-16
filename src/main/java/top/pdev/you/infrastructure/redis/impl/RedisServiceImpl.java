package top.pdev.you.infrastructure.redis.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.pdev.you.infrastructure.config.CacheProperties;
import top.pdev.you.infrastructure.redis.RedisService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存服务实现类
 * Created in 2023/1/15 12:29
 *
 * @author Peter1303
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private CacheProperties cacheProperties;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public <K, V> void set(K key, V value) {
        set(key, value, -1, null);
    }

    @Override
    public <K, V> void set(K key, V value, long timeout, TimeUnit unit) {
        try {
            if (value != null) {
                stringRedisTemplate.opsForValue().set(cacheProperties.getPrefix() + key,
                        objectMapper.writeValueAsString(value));
                if (unit != null) {
                    stringRedisTemplate.expire(cacheProperties.getPrefix() + key, timeout, unit);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("数据缓存至 redis 失败");
        }
    }

    @Override
    public <K, SK, V> void setHashCache(K key, SK subKey, V value) {
        stringRedisTemplate.opsForHash().put(cacheProperties.getPrefix() + key, subKey, value);
    }

    @Override
    public <K, SK, V> void setHashCache(K key, SK subKey, V value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForHash().put(cacheProperties.getPrefix() + key, subKey, value);
        stringRedisTemplate.expire(cacheProperties.getPrefix() + key, timeout, unit);
    }

    @Override
    public <K, SK> Object getHashCache(K key, SK subKey) {
        return stringRedisTemplate.opsForHash().get(cacheProperties.getPrefix() + key, subKey);
    }

    @Override
    public <K, V> V getObject(K key, Class<V> clazz) {
        String value = this.get(key);
        V result = null;
        if (!StrUtil.isEmpty(value)) {
            try {
                result = objectMapper.readValue(value, clazz);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public <K, V> List<V> getList(K key) {
        String value = this.get(key);
        List<V> result = Collections.emptyList();
        if (!StrUtil.isEmpty(value)) {
            try {
                result = objectMapper.readValue(value, new TypeReference<List<V>>() {
                        });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
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

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(cacheProperties.getPrefix() + key);
    }

    @Override
    public void delete(Collection<String> keys) {
        Collection<String> temp = new ArrayList<>();
        keys.forEach(key -> temp.add(cacheProperties.getPrefix() + temp));
        stringRedisTemplate.delete(keys);
    }

    @Override
    public byte[] dump(String key) {
        return stringRedisTemplate.dump(key);
    }

    @Override
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(cacheProperties.getPrefix() + key);
    }

    @Override
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    @Override
    public Boolean expireAt(String key, DateTime dateTime) {
        return stringRedisTemplate.expireAt(key, dateTime.toJdkDate());
    }

    @Override
    public Boolean persist(String key) {
        return stringRedisTemplate.persist(key);
    }

    @Override
    public Long getExpire(String key, TimeUnit unit) {
        return stringRedisTemplate.getExpire(key, unit);
    }

    @Override
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }
}
