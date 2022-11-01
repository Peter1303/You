package top.pdev.you.domain.entity.base;

import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.Student;

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
}
