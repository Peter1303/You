package top.pdev.you.domain.service;

import top.pdev.you.domain.entity.User;

/**
 * 权限服务
 * Created in 2022/8/19 21:53
 *
 * @author Peter1303
 */
public interface PermissionService {
    /**
     * 有超级管理员
     *
     * @return boolean
     */
    boolean hasSuperAdmin();

    /**
     * 是负责人
     *
     * @param uidOrToken uid 或令牌
     * @return boolean
     */
    boolean isManager(Object uidOrToken);

    /**
     * 是管理
     *
     * @param uidOrToken uid 或令牌
     * @return boolean
     */
    boolean isAdmin(Object uidOrToken);

    /**
     * 是超级管理员
     *
     * @param uidOrToken uid或令牌
     * @return boolean
     */
    boolean isSuperAdmin(Object uidOrToken);

    /**
     * 是自己
     *
     * @param user   用户
     * @param userId 用户 ID
     * @return boolean
     */
    boolean isSelf(User user, Long userId);

    /**
     * 可编辑
     *
     * @param currentUser  当前用户
     * @param targetUserId 目标用户 ID
     * @return boolean
     */
    boolean editable(User currentUser, Long targetUserId);
}
