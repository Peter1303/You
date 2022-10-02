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
     * 查找通过开放 ID
     *
     * @param openId 开放 ID
     * @return {@link User}
     */
    User findByOpenId(String openId);
}
