package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 帖子话题
 * Created in 2022/10/1 15:56
 *
 * @author Peter1303
 */
@TableName("post_topic")
@Data
public class PostTopicDO {
    /**
     * 帖子 ID
     */
    private Long postId;

    /**
     * 话题 ID
     */
    private Long topicId;
}
