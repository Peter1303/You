package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 活动参与者
 * Created in 2022/10/1 15:58
 *
 * @author Peter1303
 */
@TableName("activity_participant")
@Data
public class ActivityParticipantDO extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * uid
     */
    private Long uid;

    /**
     * 活动 ID
     */
    private Long activityId;

    /**
     * 时间
     */
    private LocalDateTime time;
}
