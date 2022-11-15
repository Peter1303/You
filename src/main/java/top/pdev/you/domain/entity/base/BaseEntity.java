package top.pdev.you.domain.entity.base;

import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;

import java.util.Optional;

/**
 * 基本实体
 * Created in 2022/9/30 22:32
 *
 * @author Peter1303
 */
public abstract class BaseEntity {
    /**
     * 检查学生
     *
     * @param student 学生
     */
    protected void check(Student student) {
        Optional.ofNullable(student).orElseThrow(() -> new InternalErrorException("学生为空"));
        Optional.ofNullable(student.getStudentDO())
                .orElseThrow(() -> new InternalErrorException("学生记录为空"));
    }

    protected void check(Teacher teacher) {
        Optional.ofNullable(teacher).orElseThrow(() -> new InternalErrorException("老师为空"));
        Optional.ofNullable(teacher.getTeacherDO())
                .orElseThrow(() -> new InternalErrorException("老师记录为空"));
    }
}
