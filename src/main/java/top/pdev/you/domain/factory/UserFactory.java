package top.pdev.you.domain.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.User;

/**
 * 用户工厂
 * Created in 2022/10/2 21:39
 *
 * @author Peter1303
 */
@Component
public class UserFactory {
    /**
     * 新用户
     *
     * @return {@link User}
     */
    public User newUser() {
        return new User(null);
    }
}
