package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 活动参与信息
 * Created in 2022/10/1 16:00
 *
 * @author Peter1303
 */
@TableName("activity_participant_info")
@Data
public class ActivityParticipantInfoDO {
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
     * 学生 ID
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 时间
     */
    private LocalDateTime time;
}
