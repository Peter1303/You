package top.pdev.you.domain.repository.impl;

import org.springframework.stereotype.Repository;
import top.pdev.you.common.constant.RedisKey;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.domain.repository.TokenRepository;
import top.pdev.you.domain.repository.base.CacheRepositoryImpl;

/**
 * 令牌仓库实现类
 * Created in 2023/1/4 15:51
 *
 * @author Peter1303
 */
@Repository
public class TokenRepositoryImpl
        extends CacheRepositoryImpl<TokenInfo>
        implements TokenRepository {
    @Override
    public TokenInfo findByToken(String token) {
        return find(RedisKey.loginToken(token), TokenInfo.class);
    }
}
