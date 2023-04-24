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

import java.util.Optional;

/**
 * 校区
 * Created in 2022/10/26 10:57
 *
 * @author Peter1303
 */
@TableName("campus")
@Data
public class Campus extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 获取学生校区名字
     *
     * @param student 学生
     * @return {@link String}
     */
    public String getStudentCampusName(Student student) {
        Long classId = student.getClassId();
        ClassRepository classRepository = SpringUtil.getBean(ClassRepository.class);
        Clazz clazz = classRepository.findById(classId);
        if (Optional.ofNullable(clazz).isPresent()) {
            CampusRepository campusRepository = SpringUtil.getBean(CampusRepository.class);
            Campus campus = campusRepository.findById(clazz.getCampusId());
            return campus.getName();
        }
        return null;
    }

    /**
     * 保存
     */
    public void save() {
        CampusRepository campusRepository = SpringUtil.getBean(CampusRepository.class);
        // 是否存在相同的
        if (campusRepository.existsByName(getName())) {
            throw new BusinessException("已经存在相同的校区");
        }
        if (!campusRepository.save(this)) {
            throw new BusinessException("保存校区失败");
        }
    }
}
