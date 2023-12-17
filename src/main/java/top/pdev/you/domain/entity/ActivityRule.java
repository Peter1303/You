package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;

/**
 * 活动规则
 * Created in 2022/10/1 15:30
 *
 * @author Peter1303
 */
@Data
public class ActivityRule extends BaseEntity {
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
