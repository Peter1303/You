package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.CampusDO;
import top.pdev.you.domain.entity.data.ClassDO;
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

    /**
     * 名字
     */
    private String name;

    public Campus(CampusDO campusDO) {
        if (!Optional.ofNullable(campusDO).isPresent()) {
            return;
        }
        this.id = campusDO.getId();
        this.name = campusDO.getName();
    }

    /**
     * 获取学生校区名字
     *
     * @param student 学生
     * @return {@link String}
     */
    public String getStudentCampusName(Student student) {
        Long classId = student.getClassId();
        ClassRepository classRepository = SpringUtil.getBean(ClassRepository.class);
        ClassDO classDO = classRepository.getDO(classId);
        if (Optional.ofNullable(classDO).isPresent()) {
            CampusRepository campusRepository = SpringUtil.getBean(CampusRepository.class);
            Campus campus = campusRepository.findById(classDO.getCampusId());
            return campus.getName();
        }
        return null;
    }

    /**
     * 保存
     *
     * @param campusDO 校园 DO
     */
    public void save(CampusDO campusDO) {
        CampusRepository campusRepository = SpringUtil.getBean(CampusRepository.class);
        // 是否存在相同的
        if (campusRepository.existsByName(campusDO.getName())) {
            throw new BusinessException("已经存在相同的校区");
        }
        if (!campusRepository.save(campusDO)) {
            throw new BusinessException("保存校区失败");
        }
    }
}
