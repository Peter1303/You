package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.ClassDO;
import top.pdev.you.domain.entity.data.InstituteDO;
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
    private String name;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private final InstituteRepository instituteRepository = SpringUtil.getBean(InstituteRepository.class);

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private final ClassRepository classRepository = SpringUtil.getBean(ClassRepository.class);

    public Institute(InstituteDO instituteDO) {
        if (!Optional.ofNullable(instituteDO).isPresent()) {
            return;
        }
        this.id = instituteDO.getId();
        this.name = instituteDO.getName();
    }

    /**
     * 获取学生学院名字
     *
     * @return {@link String}
     */
    public String getStudentInstituteName(Student student) {
        Long classId = student.getClassId();
        ClassDO classDO = classRepository.getDO(classId);
        if (Optional.ofNullable(classDO).isPresent()) {
            Institute institute = instituteRepository.findById(classDO.getInstituteId());
            return institute.getName();
        }
        return null;
    }

    /**
     * 保存
     *
     * @param instituteDO 学院 DO
     */
    public void save(InstituteDO instituteDO) {
        // 检查存在
        if (instituteRepository.existsByName(instituteDO.getName())) {
            throw new BusinessException("该学院已经存在");
        }
        if (!instituteRepository.save(instituteDO)) {
            throw new BusinessException("保存学院失败");
        }
    }
}
