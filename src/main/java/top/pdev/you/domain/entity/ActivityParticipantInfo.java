package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 活动参与信息
 * Created in 2022/10/1 16:00
 *
 * @author Peter1303
 */
@Data
public class ActivityParticipantInfo {
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
