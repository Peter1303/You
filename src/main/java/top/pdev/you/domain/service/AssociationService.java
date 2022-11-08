package top.pdev.you.domain.service;

import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.AddAssociationVO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

/**
 * 社团服务
 * Created in 2022/11/8 17:50
 *
 * @author Peter1303
 */
public interface AssociationService {
    /**
     * 添加
     *
     * @param addAssociationVO 添加社团 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> add(AddAssociationVO addAssociationVO);

    /**
     * 列表
     *
     * @param searchVO  搜索 VO
     * @param tokenInfo 令牌信息
     * @return {@link Result}<{@link ?}>
     */
    Result<?> list(SearchVO searchVO, TokenInfo tokenInfo);
}
