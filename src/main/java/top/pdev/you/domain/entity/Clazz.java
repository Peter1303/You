package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.ClassDO;
import top.pdev.you.domain.repository.CampusRepository;
import top.pdev.you.domain.repository.ClassRepository;
import top.pdev.you.domain.repository.InstituteRepository;

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

    @Getter(AccessLevel.NONE)
    private final CampusRepository campusRepository = SpringUtil.getBean(CampusRepository.class);

    @Getter(AccessLevel.NONE)
    private final InstituteRepository instituteRepository = SpringUtil.getBean(InstituteRepository.class);

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
        ClassDO classDO = classRepository.getDO(student.getClassId());
        return classDO.getName();
    }

    /**
     * 保存
     *
     * @param classDO 班级 DO
     */
    public void save(ClassDO classDO) {
        Optional.ofNullable(classDO)
                .orElseThrow(() -> new InternalErrorException("没有持久化信息"));
        Long campusId = classDO.getCampusId();
        Long instituteId = classDO.getInstituteId();
        if (!campusRepository.existsById(campusId)) {
            throw new BusinessException("没有找到该校区");
        }
        if (!instituteRepository.existsById(instituteId)) {
            throw new BusinessException("没有找到该学院");
        }
        // 检查是否重复
        if (classRepository.existsByNameAndInstituteIdAndCampusId(classDO.getName(),
                classDO.getInstituteId(),
                classDO.getCampusId())) {
            throw new BusinessException("已经存在相同的班级");
        }
        classRepository.save(classDO);
    }
}
