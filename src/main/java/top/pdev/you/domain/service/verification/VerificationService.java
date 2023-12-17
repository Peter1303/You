package top.pdev.you.domain.service.verification;

import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.domain.entity.User;

/**
 * 认证服务
 * Created in 2023/4/24 20:36
 *
 * @author Peter1303
 */
public interface VerificationService {
    TokenInfo getTokenInfo();

    User currentUser();
}
