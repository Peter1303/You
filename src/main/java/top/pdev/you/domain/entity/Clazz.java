package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.persistence.repository.CampusRepository;
import top.pdev.you.persistence.repository.ClassRepository;
import top.pdev.you.persistence.repository.InstituteRepository;

/**
 * 班级领域
 * Created in 2022/10/3 18:18
 *
 * @author Peter1303
 */
@TableName("class")
@Data
public class Clazz extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学院 ID
     */
    private Long instituteId;

    /**
     * 校区 ID
     */
    private Long campusId;

    /**
     * 名称
     */
    private String name;

    /**
     * 年级
     */
    private Integer year;

    /**
     * 保存
     */
    public void save() {
        Long campusId = getCampusId();
        Long instituteId = getInstituteId();
        CampusRepository campusRepository = SpringUtil.getBean(CampusRepository.class);
        if (!campusRepository.existsById(campusId)) {
            throw new BusinessException("没有找到该校区");
        }
        InstituteRepository instituteRepository = SpringUtil.getBean(InstituteRepository.class);
        if (!instituteRepository.existsById(instituteId)) {
            throw new BusinessException("没有找到该学院");
        }
        ClassRepository classRepository = SpringUtil.getBean(ClassRepository.class);
        // 检查是否重复
        if (classRepository.existsByNameAndInstituteIdAndCampusId(
                getName(),
                getInstituteId(),
                getCampusId())) {
            throw new BusinessException("已经存在相同的班级");
        }
        classRepository.save(this);
    }
}
