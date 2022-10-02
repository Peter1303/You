package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 活动审批
 * Created in 2022/10/1 16:02
 *
 * @author Peter1303
 */
@TableName("activity_audit")
@Data
public class ActivityAuditDO {
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
     * uid
     */
    private Long uid;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 时间
     */
    private LocalDateTime time;
}
