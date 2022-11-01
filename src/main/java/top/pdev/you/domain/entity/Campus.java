package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.CampusDO;
import top.pdev.you.domain.entity.data.ClassDO;
import top.pdev.you.domain.entity.data.StudentDO;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.domain.repository.CampusRepository;
import top.pdev.you.domain.repository.ClassRepository;

import java.util.Optional;

/**
 * 校区
 * Created in 2022/10/26 10:57
 *
 * @author Peter1303
 */
@Data
public class Campus extends BaseEntity {
    private Long id;

    private final CampusRepository campusRepository = SpringUtil.getBean(CampusRepository.class);
    private final ClassRepository classRepository = SpringUtil.getBean(ClassRepository.class);

    public Campus(CampusDO campusDO) {
        if (!Optional.ofNullable(campusDO).isPresent()) {
            return;
        }
        this.id = campusDO.getId();
    }

    /**
     * 获取学生校区名字
     *
     * @param student 学生
     * @return {@link String}
     */
    public String getStudentCampusName(Student student) {
        super.check(student);
        StudentDO studentDO = student.getStudentDO();
        Long classId = studentDO.getClassId();
        ClassDO classDO = classRepository.getDO(classId);
        if (Optional.ofNullable(classDO).isPresent()) {
            return campusRepository.getName(new Id(classDO.getCampusId()));
        }
        return null;
    }
}
