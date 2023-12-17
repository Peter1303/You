package top.pdev.you.domain.service.association;

import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.ui.vm.AssociationAuditResponse;
import top.pdev.you.domain.ui.vm.AssociationInfoResponse;
import top.pdev.you.web.association.command.AddAssociationCommand;
import top.pdev.you.web.association.command.ChangeNameCommand;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.query.command.SearchCommand;
import top.pdev.you.web.user.command.AddAdminCommand;
import top.pdev.you.web.user.command.RemoveAdminCommand;

import java.util.List;

/**
 * 社团服务
 * Created in 2022/11/8 17:50
 *
 * @author Peter1303
 */
public interface AssociationService {
    /**
     * 审核列表
     *
     * @param user 用户
     * @return {@link List}<{@link AssociationAuditResponse}>
     */
    List<AssociationAuditResponse> auditList(User user);

    /**
     * 列表
     *
     * @param user          用户
     * @param searchCommand 搜索 VO
     * @return {@link List}<{@link AssociationInfoResponse}>
     */
    List<AssociationInfoResponse> list(User user, SearchCommand searchCommand);

    /**
     * 添加
     *
     * @param addAssociationCommand 添加社团 VO
     */
    void add(AddAssociationCommand addAssociationCommand);

    /**
     * 删除
     *
     * @param idCommand ID VO
     */
    void delete(IdCommand idCommand);

    /**
     * 加入
     *
     * @param directly 直接
     * @param user     用户
     * @param idCommand     ID VO
     */
    void join(boolean directly, User user, IdCommand idCommand);

    /**
     * 通过
     *
     * @param idCommand ID VO
     */
    void pass(IdCommand idCommand);

    /**
     * 拒绝
     *
     * @param idCommand ID VO
     */
    void reject(IdCommand idCommand);

    /**
     * 添加负责人
     *
     * @param addAdminVO 添加负责人 VO
     */
    void addManager(AddAdminCommand addAdminVO);

    /**
     * 添加指导老师
     *
     * @param addAdminVO 添加指导老师 VO
     */
    void addAdmin(AddAdminCommand addAdminVO);

    /**
     * 设置名称
     *
     * @param nameVO 名字 VO
     */
    void setName(ChangeNameCommand nameVO);

    /**
     * 设置描述
     *
     * @param nameVO 名字 VO
     */
    void setSummary(ChangeNameCommand nameVO);

    /**
     * 删除负责人
     *
     * @param removeAdminVO 删除管理 VO
     */
    void removeManager(RemoveAdminCommand removeAdminVO);

    /**
     * 删除指导老师
     *
     * @param removeAdminVO 删除管理 VO
     */
    void removeAdmin(RemoveAdminCommand removeAdminVO);

    /**
     * 归属社团
     *
     * @param role 角色
     * @return {@link Association}
     */
    Association belongAssociation(RoleEntity role);
}
