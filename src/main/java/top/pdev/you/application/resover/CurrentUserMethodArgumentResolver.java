package top.pdev.you.application.resover;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.pdev.you.common.annotation.CurrentUser;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.service.verification.VerificationService;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 当前用户解析器
 * Created in 2022/10/19 10:10
 *
 * @author Peter1303
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Resource
    private VerificationService verificationService;

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
        if (parameter.getParameterType().isAssignableFrom(User.class)) {
            return verificationService.currentUser();
        } else {
            return verificationService.getTokenInfo();
        }
    }
}
