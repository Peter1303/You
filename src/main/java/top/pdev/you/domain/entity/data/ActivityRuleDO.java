package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 活动规则
 * Created in 2022/10/1 15:30
 *
 * @author Peter1303
 */
@TableName("activity_rule")
@Data
public class ActivityRuleDO {
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
     * 规则
     */
    private Integer rule;

    /**
     * 值
     */
    private String value;
}
