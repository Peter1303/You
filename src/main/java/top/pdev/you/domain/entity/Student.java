package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.data.StudentDO;
import top.pdev.you.domain.entity.types.StudentId;
import top.pdev.you.domain.factory.AssociationFactory;
import top.pdev.you.domain.repository.ClassRepository;
import top.pdev.you.domain.repository.StudentRepository;
import top.pdev.you.infrastructure.result.ResultCode;

import java.util.Optional;

/**
 * 学生领域
 * Created in 2022/10/3 18:11
 *
 * @author Peter1303
 */
@Data
public class Student {
    private User user;
    private StudentId studentId;
    private String no;
    private String name;
    private String contact;

    private final StudentRepository studentRepository = SpringUtil.getBean(StudentRepository.class);
    private final ClassRepository classRepository = SpringUtil.getBean(ClassRepository.class);
    private final AssociationFactory associationFactory = SpringUtil.getBean(AssociationFactory.class);

    public Student(User user) {
        if (!Optional.ofNullable(user).isPresent()) {
            return;
        }
        this.user = user;
        this.studentId = new StudentId(user.getTargetId());
        StudentDO studentDO = studentRepository.getDO(studentId);
        this.name = studentDO.getName();
        this.no = studentDO.getNo();
        this.contact = studentDO.getContact();
    }

    public Association getAssociationUnderManaged() {
        return associationFactory.getAssociation();
    }

    /**
     * 保存
     *
     * @param studentDO 学生 DO
     */
    public void save(StudentDO studentDO) {
        // 检测班级是否存在
        Long classId = studentDO.getClassId();
        if (!Optional.ofNullable(classRepository.find(classId)).isPresent()) {
            throw new BusinessException(ResultCode.FAILED, "班级不存在");
        }
        if (!Optional.ofNullable(classId).isPresent()) {
            throw new InternalErrorException("班级 ID 空错误");
        }
        if (!studentRepository.save(studentDO)) {
            throw new InternalErrorException("无法保存学生");
        }
    }
}
