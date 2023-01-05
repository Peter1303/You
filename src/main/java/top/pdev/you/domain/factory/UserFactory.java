package top.pdev.you.domain.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
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
        return new User();
    }

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

    /**
     * 获取负责人
     *
     * @param user 用户
     * @return {@link Manager}
     */
    public Manager getManager(User user) {
        return new Manager(user);
    }

    /**
     * 获取负责人
     *
     * @param student 学生
     * @return {@link Manager}
     */
    public Manager getManager(Student student) {
        return new Manager(student.getUser());
    }

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
