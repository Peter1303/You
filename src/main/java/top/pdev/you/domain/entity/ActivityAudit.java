package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 活动审批
 * Created in 2022/10/1 16:02
 *
 * @author Peter1303
 */
@TableName("activity_audit")
@Data
public class ActivityAudit extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 活动 ID
     */
    private Long activityId;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 时间
     */
    private LocalDateTime time;
}
