package top.pdev.you.domain.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.data.UserDO;

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
        return getUser(null);
    }

    /**
     * 获取用户
     *
     * @param userDO 用户 DO
     * @return {@link User}
     */
    public User getUser(UserDO userDO) {
        return new User(userDO);
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
