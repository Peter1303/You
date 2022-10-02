package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论
 * Created in 2022/10/1 14:25
 *
 * @author Peter1303
 */
@TableName("comment")
@Data
public class CommentDO {
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
     * uid
     */
    private Long uid;

    /**
     * 评论
     */
    private String comment;

    /**
     * 时间
     */
    private LocalDateTime time;
}
