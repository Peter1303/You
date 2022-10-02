package top.pdev.you.domain.entity;

import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.UserDO;
import top.pdev.you.domain.entity.types.UserId;

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

    public User(UserDO userDO) {
        this.userId = new UserId(userDO.getId());
        this.permission = userDO.getPermission();
    }
}
