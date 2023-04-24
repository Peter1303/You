package top.pdev.you.common.interceptor;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.pdev.you.common.constant.Constants;
import top.pdev.you.common.enums.RedisKey;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.common.exception.TokenInvalidException;
import top.pdev.you.domain.entity.User;
import top.pdev.you.persistence.repository.TokenRepository;
import top.pdev.you.persistence.repository.UserRepository;
import top.pdev.you.infrastructure.util.TagKeyUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 令牌检查
 * Created in 2022/4/19 23:26
 *
 * @author Peter1303
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Resource
    private UserRepository userRepository;

    @Resource
    private TokenRepository tokenRepository;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler)
            throws Exception {
        // 不是处理方法那么跳过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 取出请求头的 token
        String token = request.getHeader(Constants.TOKEN);
        // 如果有
        if (StrUtil.isNotBlank(token)) {
            // 从缓存中读取 若令牌已过期那么阻止
            String tag = TagKeyUtil.get(RedisKey.LOGIN_TOKEN, token);
            if (!tokenRepository.exists(tag)) {
                User user = userRepository.findByWechatId(token);
                Optional.ofNullable(user).orElseThrow(TokenInvalidException::new);
                TokenInfo info = new TokenInfo();
                info.setUid(user.getId());
                // 缓存一个月
                tokenRepository.save(tag, info, 30, TimeUnit.DAYS);
            }
        }
        return true;
    }
}
