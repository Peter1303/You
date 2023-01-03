package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 活动
 * Created in 2022/10/1 15:24
 *
 * @author Peter1303
 */
@TableName("activity")
@Data
public class Activity extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String summary;

    /**
     * 位置
     */
    private String location;

    /**
     * 活动时间
     */
    private LocalDateTime time;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
