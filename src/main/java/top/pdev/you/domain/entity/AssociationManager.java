package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.factory.AssociationFactory;
import top.pdev.you.domain.repository.AssociationManagerRepository;

import java.util.Optional;

/**
 * 社团管理领域
 * Created in 2023/1/3 16:45
 *
 * @author Peter1303
 */
@TableName("association_manager")
@Data
public class AssociationManager extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 社团 ID
     */
    private Long associationId;

    /**
     * 用户 ID
     */
    @TableField("uid")
    private Long userId;

    /**
     * 类型
     */
    private Integer type;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private final AssociationManagerRepository associationManagerRepository =
            SpringUtil.getBean(AssociationManagerRepository.class);

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private final AssociationFactory associationFactory = SpringUtil.getBean(AssociationFactory.class);

    /**
     * 添加管理
     *
     * @param student 学生
     */
    public void addAdmin(Student student) {
        AssociationManager associationManger = associationFactory.newAssociationManger();
        associationManger.setAssociationId(getId());
        associationManger.setUserId(student.getUserId());
        // 变更权限
        student.getUser().permissionTo(Permission.MANAGER);
        associationManger.setType(Permission.MANAGER.getValue());
        setAdmin(associationManger);
    }

    /**
     * 添加管理
     *
     * @param teacher 老师
     */
    public void addAdmin(Teacher teacher) {
        AssociationManager associationManger = associationFactory.newAssociationManger();
        associationManger.setAssociationId(getId());
        associationManger.setUserId(teacher.getUserId());
        associationManger.setType(Permission.ADMIN.getValue());
        setAdmin(associationManger);
    }

    /**
     * 设置管理
     *
     * @param associationManger 管理
     */
    private void setAdmin(AssociationManager associationManger) {
        if (!associationManagerRepository.save(associationManger)) {
            throw new BusinessException("无法保存社团管理人员");
        }
    }

    /**
     * 管理存在
     *
     * @param user 用户
     * @return boolean
     */
    public boolean adminExists(User user) {
        Optional.ofNullable(user)
                .orElseThrow(() -> new BusinessException("找不到用户"));
        return associationManagerRepository.existsByAssociationIdAndUserIdAndType(getId(),
                user.getId(),
                user.getPermission());
    }
}
