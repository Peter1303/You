package top.pdev.you.infrastructure.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.persistence.repository.StudentRepository;
import top.pdev.you.persistence.repository.TeacherRepository;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 用户工厂
 * Created in 2022/10/2 21:39
 *
 * @author Peter1303
 */
@Component
public class UserFactory {
    @Resource
    private TeacherRepository teacherRepository;

    @Resource
    private StudentRepository studentRepository;

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
        if (!Optional.ofNullable(user).isPresent()) {
            return new Student();
        }
        Long userId = user.getId();
        Student student = studentRepository.findByUserId(userId);
        Optional.ofNullable(student)
                .orElseThrow(() -> new BusinessException("没有找到学生"));
        return student;
    }

    /**
     * 获取负责人
     *
     * @param user 用户
     * @return {@link Manager}
     */
    public Manager getManager(User user) {
        Student student = studentRepository.findByUserId(user.getId());
        return new Manager(student);
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
        if (!Optional.ofNullable(user).isPresent()) {
            return new Teacher();
        }
        Long userId = user.getId();
        return teacherRepository.findByUserId(userId);
    }
}
