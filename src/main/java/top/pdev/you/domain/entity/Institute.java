package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.ClassDO;
import top.pdev.you.domain.entity.data.InstituteDO;
import top.pdev.you.domain.entity.data.StudentDO;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.domain.repository.ClassRepository;
import top.pdev.you.domain.repository.InstituteRepository;

import java.util.Optional;

/**
 * 学院领域
 * Created in 2022/10/26 11:32
 *
 * @author Peter1303
 */
@Data
public class Institute extends BaseEntity {
    private Long id;

    private final InstituteRepository instituteRepository = SpringUtil.getBean(InstituteRepository.class);
    private final ClassRepository classRepository = SpringUtil.getBean(ClassRepository.class);

    public Institute(InstituteDO instituteDO) {
        if (!Optional.ofNullable(instituteDO).isPresent()) {
            return;
        }
        this.id = instituteDO.getId();
    }

    /**
     * 获取学生学院名字
     *
     * @return {@link String}
     */
    public String getStudentInstituteName(Student student) {
        super.checkStudent(student);
        StudentDO studentDO = student.getStudentDO();
        Long classId = studentDO.getClassId();
        ClassDO classDO = classRepository.getDO(classId);
        if (Optional.ofNullable(classDO).isPresent()) {
            return instituteRepository.getName(new Id(classDO.getInstituteId()));
        }
        return null;
    }
}
