package top.pdev.you.domain.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.User;

/**
 * 学生工厂
 * Created in 2022/10/3 18:15
 *
 * @author Peter1303
 */
@Component
public class StudentFactory {
    /**
     * 新学生
     *
     * @return {@link Student}
     */
    public Student newStudent() {
        return getStudent(null);
    }

    /**
     * 获取学生
     *
     * @param user 用户
     * @return {@link Student}
     */
    public Student getStudent(User user) {
        return new Student(user);
    }
}
