package top.pdev.you.application.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import top.pdev.you.common.enums.RedisKey;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.infrastructure.redis.RedisService;
import top.pdev.you.infrastructure.result.ResultCode;
import top.pdev.you.infrastructure.util.RequestUtil;
import top.pdev.you.infrastructure.util.TagKeyUtil;
import top.pdev.you.infrastructure.util.TokenUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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

    /**
     * 检查登录
     *
     * @param point 切入点
     * @return {@link Object}
     */
    @Around("execution(* top.pdev.you.web..*.*(..)) " +
            "&& !@annotation(top.pdev.you.common.annotation.SkipCheckLogin) ")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = RequestUtil.getRequest();
        String token = TokenUtil.getTokenByHeader(request);
        // 检验 Token
        TokenInfo tokenInfo = redisService.getObject(TagKeyUtil.get(RedisKey.LOGIN_TOKEN, token), TokenInfo.class);
        if (!Optional.ofNullable(tokenInfo).isPresent()) {
            // 没有缓存记录
            throw new BusinessException(ResultCode.PERMISSION_DENIED);
        }
        // 执行后续的方法
        return point.proceed();
    }
}
