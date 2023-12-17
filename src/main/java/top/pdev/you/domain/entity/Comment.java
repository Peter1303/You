package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 评论领域
 * Created in 2023/1/2 21:04
 *
 * @author Peter1303
 */
@Data
public class Comment extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 帖子 ID
     */
    private Long postId;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 评论
     */
    private String comment;

    /**
     * 时间
     */
    private LocalDateTime time;
}
