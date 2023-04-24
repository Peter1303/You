package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.persistence.repository.AssociationAuditRepository;

import java.time.LocalDateTime;

/**
 * 社团审核
 * Created in 2022/10/1 14:15
 *
 * @author Peter1303
 */
@TableName("association_audit")
@Data
public class AssociationAudit extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 社团 ID
     */
    private Long associationId;

    /**
     * 学生 ID
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 通过
     */
    public void pass() {
        changeStatus(true);
    }

    /**
     * 拒绝
     */
    public void reject() {
        changeStatus(false);
    }

    private void changeStatus(boolean status) {
        AssociationAudit audit = new AssociationAudit();
        audit.setId(id);
        audit.setStatus(status);
        AssociationAuditRepository associationAuditRepository =
                SpringUtil.getBean(AssociationAuditRepository.class);
        boolean update = associationAuditRepository.updateById(audit);
        if (!update) {
            throw new BusinessException("无法更改入团审核状态");
        }
        setStatus(status);
    }
}
