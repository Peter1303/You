package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 点赞
 * Created in 2022/10/1 14:26
 *
 * @author Peter1303
 */
@TableName("like")
@Data
public class LikeDO {
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
}
