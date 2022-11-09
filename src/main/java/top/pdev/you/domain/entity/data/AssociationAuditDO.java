package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 社团审核
 * Created in 2022/10/1 14:15
 *
 * @author Peter1303
 */
@TableName("association_audit")
@Data
public class AssociationAuditDO {
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
}
