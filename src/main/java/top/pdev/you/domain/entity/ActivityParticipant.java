package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 活动参与者
 * Created in 2023/4/25 20:07
 *
 * @author Peter1303
 */
@Data
@TableName("activity_participant")
public class ActivityParticipant {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 活动 ID
     */
    private Long activityId;

    /**
     * 时间
     */
    private LocalDateTime time;
}
