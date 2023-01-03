package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.repository.ClassRepository;
import top.pdev.you.domain.repository.InstituteRepository;

import java.util.Optional;

/**
 * 学院领域
 * Created in 2022/10/26 11:32
 *
 * @author Peter1303
 */
@TableName("institute")
@Data
public class Institute extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 获取学生学院名字
     *
     * @return {@link String}
     */
    public String getStudentInstituteName(Student student) {
        Long classId = student.getClassId();
        ClassRepository classRepository = SpringUtil.getBean(ClassRepository.class);
        Clazz clazz = classRepository.findById(classId);
        if (Optional.ofNullable(clazz).isPresent()) {
            InstituteRepository instituteRepository = SpringUtil.getBean(InstituteRepository.class);
            Institute institute = instituteRepository.findById(clazz.getInstituteId());
            return institute.getName();
        }
        return null;
    }

    /**
     * 保存
     *
     */
    public void save() {
        InstituteRepository instituteRepository = SpringUtil.getBean(InstituteRepository.class);
        // 检查存在
        if (instituteRepository.existsByName(getName())) {
            throw new BusinessException("该学院已经存在");
        }
        if (!instituteRepository.save(this)) {
            throw new BusinessException("保存学院失败");
        }
    }
}
