package top.pdev.you.domain.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;

/**
 * 老师工厂
 * Created in 2022/10/3 16:25
 *
 * @author Peter1303
 */
@Component
public class TeacherFactory {
    /**
     * 新老师
     *
     * @return {@link Teacher}
     */
    public Teacher newTeacher() {
        return getTeacher(null);
    }

    /**
     * 获取老师
     *
     * @param user 用户
     * @return {@link Teacher}
     */
    public Teacher getTeacher(User user) {
        return new Teacher(user);
    }
}
