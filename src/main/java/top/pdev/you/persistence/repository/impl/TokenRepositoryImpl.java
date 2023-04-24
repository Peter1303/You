package top.pdev.you.persistence.repository.impl;

import org.springframework.stereotype.Repository;
import top.pdev.you.common.enums.RedisKey;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.persistence.repository.TokenRepository;
import top.pdev.you.persistence.repository.base.CacheRepositoryImpl;
import top.pdev.you.infrastructure.util.TagKeyUtil;

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
        return find(TagKeyUtil.get(RedisKey.LOGIN_TOKEN, token), TokenInfo.class);
    }
}
