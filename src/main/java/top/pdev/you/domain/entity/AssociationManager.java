package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
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
    private Long userId;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 添加管理
     *
     * @param student 学生
     */
    public void add(Student student) {
        setUserId(student.getUserId());
        // 变更权限
        student.getUser().permissionTo(Permission.MANAGER);
        setType(Permission.MANAGER.getValue());
        setAdmin();
    }

    /**
     * 添加管理
     *
     * @param teacher 老师
     */
    public void add(Teacher teacher) {
        setUserId(teacher.getUserId());
        setType(Permission.ADMIN.getValue());
        setAdmin();
    }

    /**
     * 设置管理
     */
    private void setAdmin() {
        AssociationManagerRepository associationManagerRepository =
                SpringUtil.getBean(AssociationManagerRepository.class);
        if (!associationManagerRepository.save(this)) {
            throw new BusinessException("无法保存社团管理人员");
        }
    }

    /**
     * 管理存在
     *
     * @param role 用户
     * @return boolean
     */
    public boolean exists(RoleEntity role) {
        Optional.ofNullable(role)
                .orElseThrow(() -> new BusinessException("找不到用户角色"));
        AssociationManagerRepository associationManagerRepository =
                SpringUtil.getBean(AssociationManagerRepository.class);
        return associationManagerRepository.existsByAssociationIdAndUserIdAndType(
                getAssociationId(),
                role.getUser().getId(),
                role.getUser().getPermission());
    }
}
