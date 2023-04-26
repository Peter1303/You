package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long uid;

    @TableField("activity_id")
    private Long activityId;

    private LocalDateTime time;
}
