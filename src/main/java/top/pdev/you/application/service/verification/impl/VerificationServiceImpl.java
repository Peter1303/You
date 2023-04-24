package top.pdev.you.application.service.verification.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import top.pdev.you.application.service.verification.VerificationService;
import top.pdev.you.common.constant.Constants;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.common.exception.TokenInvalidException;
import top.pdev.you.domain.entity.User;
import top.pdev.you.infrastructure.util.RequestUtil;
import top.pdev.you.persistence.repository.TokenRepository;
import top.pdev.you.persistence.repository.UserRepository;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 认证服务实现
 * Created in 2023/4/24 20:37
 *
 * @author Peter1303
 */
@Service
public class VerificationServiceImpl implements VerificationService {
    @Resource
    private TokenRepository tokenRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    public TokenInfo getTokenInfo() {
        // 取出请求头的 token
        String token = RequestUtil.getRequest().getHeader(Constants.TOKEN);
        if (StrUtil.isBlank(token)) {
            throw new TokenInvalidException();
        }
        return tokenRepository.findByToken(token);
    }

    @Override
    public User currentUser() {
        User object = userRepository.findById(getTokenInfo().getUid());
        Optional.ofNullable(object)
                .orElseThrow(TokenInvalidException::new);
        return object;
    }
}
