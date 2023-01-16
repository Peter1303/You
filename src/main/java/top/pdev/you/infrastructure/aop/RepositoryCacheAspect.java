package top.pdev.you.infrastructure.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.pdev.you.common.annotation.Cache;
import top.pdev.you.common.annotation.DeleteCache;
import top.pdev.you.common.provider.CacheHandler;
import top.pdev.you.infrastructure.redis.RedisService;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 仓库缓存 AOP
 * Created in 2023/1/16 11:41
 *
 * @author Peter1303
 */
@Aspect
@Component
public class RepositoryCacheAspect {
    @Resource
    private RedisService redisService;

    @Around("execution(* top.pdev.you.domain.repository.*.*(..)) " +
            "&& @annotation(top.pdev.you.common.annotation.Cache) ")
    public Object cache(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Cache cache = method.getAnnotation(Cache.class);
        Object[] args = point.getArgs();
//        Class<?> type = method.getDeclaringClass();
        String methodName = method.getName();
        return new CacheHandler(redisService)
                .sec(cache.sec())
                .type(cache.value())
                .method(methodName)
                .params(args)
                .provider(point::proceed)
                .cache();
    }

    @Around("@annotation(top.pdev.you.common.annotation.DeleteCache) ")
    public Object deleteCache(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        DeleteCache cache = method.getAnnotation(DeleteCache.class);
        new CacheHandler(redisService)
                .type(cache.value())
                .remove();
        return point.proceed();
    }
}
