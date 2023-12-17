package top.pdev.you.application.service.association;

import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.domain.entity.Association;

/**
 * 社团管理服务
 * Created in 2023/12/16 10:24
 *
 * @author Peter1303
 */
public interface AssociationManegeService {
    /**
     * 添加管理员
     *
     * @param association 协会
     * @param role        角色
     */
    void add(Association association, RoleEntity role);

    /**
     * 存在
     *
     * @param association 协会
     * @param role        角色
     * @return boolean
     */
    boolean exists(Association association, RoleEntity role);

    /**
     * 移除管理员
     *
     * @param association 协会
     * @param role        角色
     */
    void remove(Association association, RoleEntity role);
}
