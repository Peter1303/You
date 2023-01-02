package top.pdev.you.common.entity.role;

import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.User;

/**
 * 负责人实体
 * Created in 2023/1/2 22:46
 *
 * @author Peter1303
 */
public class ManagerEntity extends Student {
    public ManagerEntity(User user) {
        super(user);
    }
}
