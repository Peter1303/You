package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.UserDO;
import top.pdev.you.domain.entity.types.UserId;
import top.pdev.you.domain.repository.UserRepository;

import java.util.Optional;

/**
 * 用户领域
 * Created in 2022/9/30 22:32
 *
 * @author Peter1303
 */
@Data
public class User extends BaseEntity {
    private UserId userId;
    private Integer permission;

    private final UserRepository userRepository = SpringUtil.getBean(UserRepository.class);

    public User(UserDO userDO) {
        if (!Optional.ofNullable(userDO).isPresent()) {
            return;
        }
        this.userId = new UserId(userDO.getId());
        this.permission = userDO.getPermission();
    }

    /**
     * 保存
     *
     * @param userDO 用户 DO
     */
    public void save(UserDO userDO) {
        if (!userRepository.save(userDO)) {
            throw new InternalErrorException("无法保存用户");
        }
    }
}
