package top.pdev.you.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.User;

/**
 * 用户仓库
 * Created in 2022/10/2 13:14
 *
 * @author Peter1303
 */
public interface UserRepository extends IService<User> {
    /**
     * 查找
     *
     * @param userId 用户 ID
     * @return {@link User}
     */
    User findById(Long userId);

    /**
     * 通过 微信 ID 查找
     *
     * @param  wechatId 微信 ID
     * @return {@link User}
     */
    User findByWechatId(String wechatId);

    /**
     * 超级管理存在
     *
     * @return {@link Boolean}
     */
    Boolean superAdminExists();

    /**
     * 通过 ID 删除
     *
     * @param id ID
     * @return boolean
     */
    boolean deleteById(Long id);
}
