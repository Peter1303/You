package top.pdev.you.infrastructure.resover;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.pdev.you.common.annotation.CurrentUser;
import top.pdev.you.common.constant.Constants;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.common.exception.TokenInvalidException;
import top.pdev.you.domain.entity.User;
import top.pdev.you.persistence.repository.TokenRepository;
import top.pdev.you.persistence.repository.UserRepository;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Optional;

/**
 * 当前用户解析器
 * Created in 2022/10/19 10:10
 *
 * @author Peter1303
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Resource
    private TokenRepository tokenRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?>[] supports = {
                TokenInfo.class,
                User.class
        };
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && Arrays.stream(supports)
                .anyMatch(clazz ->
                        parameter.getParameterType().isAssignableFrom(clazz));
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
        Object object;
        // 从缓存中读取
        TokenInfo tokenInfo = tokenRepository.findByToken(token);
        if (parameter.getParameterType().isAssignableFrom(User.class)) {
            Long uid = tokenInfo.getUid();
            object = userRepository.findById(uid);
        } else {
            object = tokenInfo;
        }
        Optional.ofNullable(object)
                .orElseThrow(TokenInvalidException::new);
        return object;
    }
}
