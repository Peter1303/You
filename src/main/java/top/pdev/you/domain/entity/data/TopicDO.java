package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 话题
 * Created in 2022/10/1 14:27
 *
 * @author Peter1303
 */
@TableName("topic")
@Data
public class TopicDO {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 话题
     */
    private String name;

    /**
     * uid
     */
    private Long uid;
}
