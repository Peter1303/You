package top.pdev.you.infrastructure.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import top.pdev.you.common.constant.RedisKey;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.infrastructure.redis.RedisService;
import top.pdev.you.infrastructure.result.ResultCode;
import top.pdev.you.infrastructure.util.RequestUtil;
import top.pdev.you.infrastructure.util.TokenUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 检查登录切面
 * Created in 2022/8/19 19:07
 *
 * @author Peter1303
 */
@Slf4j
@Aspect
@Component
public class CheckLoginAspect {
    @Resource
    private RedisService redisService;

    @Resource
    private UserRepository userRepository;

    /**
     * 检查登录
     *
     * @param point 切入点
     * @return {@link Object}
     */
    @Around("execution(* top.pdev.you.interfaces.facade.*.*(..)) " +
            "&& !@annotation(top.pdev.you.common.annotation.SkipCheckLogin) ")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = RequestUtil.getRequest();
        String token = TokenUtil.getTokenByHeader(request);
        // 检验 Token
        Integer uid = redisService.getObject(RedisKey.loginToken(token), Integer.class);
        if (!Optional.ofNullable(uid).isPresent()) {
            // 没有缓存记录
            User user = userRepository.findByOpenId(token);
            if (!Optional.ofNullable(user).isPresent()) {
                throw new BusinessException(ResultCode.PERMISSION_DENIED);
            }
            // 缓存记录
            redisService.set(RedisKey.loginToken(token),
                    user.getUserId().getId(),
                    7, TimeUnit.DAYS);
        }
        // 执行后续的方法
        return point.proceed();
    }
}
