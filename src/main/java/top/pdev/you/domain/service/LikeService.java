package top.pdev.you.domain.service;

import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.IdVO;

/**
 * 点赞服务
 * Created in 2023/1/2 19:13
 *
 * @author Peter1303
 */
public interface LikeService {
    /**
     * 添加
     *
     * @param tokenInfo 令牌信息
     * @param idVO      ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> add(TokenInfo tokenInfo, IdVO idVO);

    /**
     * 删除
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(IdVO idVO);
}
