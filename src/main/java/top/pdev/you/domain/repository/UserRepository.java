package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.data.UserDO;
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
}
