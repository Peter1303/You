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
    protected void checkStudent(Student student) {
        Optional.ofNullable(student).orElseThrow(() -> new InternalErrorException("学生为空"));
        Optional.ofNullable(student.getUser()).orElseThrow(() -> new InternalErrorException("用户为空"));
        Optional.ofNullable(student.getUser().getUserId())
                .orElseThrow(() -> new InternalErrorException("用户 ID 为空"));
    }
}
