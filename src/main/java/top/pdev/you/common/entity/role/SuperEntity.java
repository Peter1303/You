package top.pdev.you.common.entity.role;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.factory.UserFactory;

/**
 * 超级管理员
 * Created in 2023/1/3 10:33
 *
 * @author Peter1303
 */
@Data
public class SuperEntity extends RoleEntity {
    private String name = "超级管理员";

    @Override
    public User getUser() {
        return SpringUtil.getBean(UserFactory.class).newUser();
    }
}
