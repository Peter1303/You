package top.pdev.you.persistence.repository;

import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.persistence.repository.base.interfaces.CacheRepository;

/**
 * 令牌仓库
 * Created in 2023/1/4 15:50
 *
 * @author Peter1303
 */
public interface TokenRepository extends CacheRepository<TokenInfo> {
    /**
     * 通过令牌查找
     *
     * @param token 令牌
     * @return {@link TokenInfo}
     */
    TokenInfo findByToken(String token);
}
