package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.data.UserDO;
import top.pdev.you.domain.entity.types.StudentId;
import top.pdev.you.domain.entity.types.TeacherId;
import top.pdev.you.domain.entity.types.UserId;

/**
 * 用户仓库
 * Created in 2022/10/2 13:14
 *
 * @author Peter1303
 */
public interface UserRepository extends IService<UserDO> {
    /**
     * 查找
     *
     * @param userId 用户 ID
     * @return {@link User}
     */
    User find(UserId userId);

    /**
     * 查找
     *
     * @param studentId 学生 ID
     * @return {@link User}
     */
    User find(StudentId studentId);

    User find(TeacherId teacherId);

    /**
     * 通过令牌查找
     *
     * @param openId 开放 ID
     * @return {@link User}
     */
    User findByToken(String openId);

    /**
     * 超级管理存在
     *
     * @return {@link Boolean}
     */
    Boolean superAdminExists();

    /**
     * 删除
     *
     * @param id ID
     * @return boolean
     */
    boolean delete(UserId id);

    /**
     * 设置权限
     *
     * @param userId     用户 ID
     * @param permission 权限
     * @return boolean
     */
    boolean setPermission(UserId userId, Permission permission);
}
