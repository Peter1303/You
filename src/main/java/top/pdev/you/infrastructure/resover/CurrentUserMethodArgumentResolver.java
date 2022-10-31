package top.pdev.you.infrastructure.resover;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.pdev.you.common.annotation.CurrentUser;
import top.pdev.you.common.constant.Constants;
import top.pdev.you.common.constant.RedisKey;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.common.exception.TokenInvalidException;
import top.pdev.you.infrastructure.redis.RedisService;

import java.util.Optional;

/**
 * 当前用户解析器
 * Created in 2022/10/19 10:10
 *
 * @author Peter1303
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final RedisService redisService = SpringUtil.getBean(RedisService.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && parameter.getParameterType().isAssignableFrom(TokenInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory)
            throws Exception {
        // 取出请求头的 token
        String token = webRequest.getHeader(Constants.TOKEN);
        if (StrUtil.isBlank(token)) {
            throw new TokenInvalidException();
        }
        // 从缓存中读取
        TokenInfo object = redisService.getObject(RedisKey.loginToken(token), TokenInfo.class);
        Optional.ofNullable(object)
                .orElseThrow(TokenInvalidException::new);
        return object;
    }
}
