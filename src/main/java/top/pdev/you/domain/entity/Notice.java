package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 公告
 * Created in 2022/10/1 15:23
 *
 * @author Peter1303
 */
@TableName("notice")
@Data
public class Notice extends BaseEntity {
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
     * 社团 ID
     */
    private Long associationId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 时间
     */
    private LocalDateTime time;
}
