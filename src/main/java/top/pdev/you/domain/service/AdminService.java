package top.pdev.you.domain.service;

/**
 * 管理员服务
 * Created in 2022/8/19 21:53
 *
 * @author Peter1303
 */
public interface AdminService {
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
}
