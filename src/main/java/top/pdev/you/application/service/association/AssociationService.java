package top.pdev.you.application.service.association;

import top.pdev.you.domain.entity.User;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.web.user.command.AddAdminCommand;
import top.pdev.you.web.association.command.AddAssociationCommand;
import top.pdev.you.web.association.command.ChangeNameCommand;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.user.command.RemoveAdminCommand;
import top.pdev.you.web.query.command.SearchCommand;

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
     * @param addAssociationCommand 添加社团 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> add(AddAssociationCommand addAssociationCommand);

    /**
     * 删除
     *
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(IdCommand idCommand);

    /**
     * 列表
     *
     * @param user     用户
     * @param searchCommand 搜索 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> list(User user, SearchCommand searchCommand);

    /**
     * 加入
     *
     * @param directly 直接
     * @param user     用户
     * @param idCommand     ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> join(boolean directly, User user, IdCommand idCommand);

    /**
     * 审核列表
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    Result<?> auditList(User user);

    /**
     * 通过
     *
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> pass(IdCommand idCommand);

    /**
     * 拒绝
     *
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> reject(IdCommand idCommand);

    /**
     * 添加负责人
     *
     * @param addAdminVO 添加负责人 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> addManager(AddAdminCommand addAdminVO);

    /**
     * 添加指导老师
     *
     * @param addAdminVO 添加指导老师 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> addAdmin(AddAdminCommand addAdminVO);

    /**
     * 设置名称
     *
     * @param nameVO 名字 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> setName(ChangeNameCommand nameVO);

    /**
     * 设置描述
     *
     * @param nameVO 名字 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> setSummary(ChangeNameCommand nameVO);

    /**
     * 删除负责人
     *
     * @param removeAdminVO 删除管理 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> removeManager(RemoveAdminCommand removeAdminVO);

    /**
     * 删除指导老师
     *
     * @param removeAdminVO 删除管理 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> removeAdmin(RemoveAdminCommand removeAdminVO);
}
