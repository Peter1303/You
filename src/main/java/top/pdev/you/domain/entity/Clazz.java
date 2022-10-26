package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.ClassDO;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.domain.repository.ClassRepository;

import java.util.Optional;

/**
 * 班级领域
 * Created in 2022/10/3 18:18
 *
 * @author Peter1303
 */
@Data
public class Clazz extends BaseEntity {
    private Long id;
    private Integer grade;

    @Getter(AccessLevel.NONE)
    private final ClassRepository classRepository = SpringUtil.getBean(ClassRepository.class);

    public Clazz(ClassDO classDO) {
        if (!Optional.ofNullable(classDO).isPresent()) {
            return;
        }
        this.id = classDO.getId();
        this.grade = classDO.getYear();
    }

    /**
     * 获取学生班级名字
     *
     * @param student 学生
     * @return {@link String}
     */
    public String getStudentClassName(Student student) {
        super.checkStudent(student);
        return classRepository.getName(new Id(student.getStudentDO().getClassId()));
    }
}
