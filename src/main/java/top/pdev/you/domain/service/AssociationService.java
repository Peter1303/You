package top.pdev.you.domain.service;

import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.AddAdminVO;
import top.pdev.you.interfaces.model.vo.req.AddAssociationVO;
import top.pdev.you.interfaces.model.vo.req.ChangeNameVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;
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
     * 删除
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(IdVO idVO);

    /**
     * 列表
     *
     * @param searchVO  搜索 VO
     * @param tokenInfo 令牌信息
     * @return {@link Result}<{@link ?}>
     */
    Result<?> list(SearchVO searchVO, TokenInfo tokenInfo);

    /**
     * 加入
     *
     * @param directly  直接
     * @param tokenInfo 令牌信息
     * @param idVO      ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> join(boolean directly, TokenInfo tokenInfo, IdVO idVO);

    /**
     * 审核列表
     *
     * @return {@link Result}<{@link ?}>
     */
    Result<?> auditList();

    /**
     * 通过
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> pass(IdVO idVO);

    /**
     * 拒绝
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> reject(IdVO idVO);

    /**
     * 添加负责人
     *
     * @param addAdminVO 添加负责人 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> addManager(AddAdminVO addAdminVO);

    /**
     * 添加指导老师
     *
     * @param addAdminVO 添加指导老师 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> addAdmin(AddAdminVO addAdminVO);

    /**
     * 设置名称
     *
     * @param nameVO 名字 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> setName(ChangeNameVO nameVO);

    /**
     * 设置描述
     *
     * @param nameVO 名字 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> setSummary(ChangeNameVO nameVO);
}
