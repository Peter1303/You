package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;

/**
 * 帖子话题
 * Created in 2022/10/1 15:56
 *
 * @author Peter1303
 */
@TableName("post_topic")
@Data
public class PostTopic extends BaseEntity {
    /**
     * 帖子 ID
     */
    private Long postId;

    /**
     * 话题 ID
     */
    private Long topicId;
}
