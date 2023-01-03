package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户
 * Created in 2022/9/30 22:34
 *
 * @author Peter1303
 */
@TableName("user")
@Data
public class UserDO {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 微信 ID
     */
    private String wechatId;

    /**
     * 权限
     */
    private Integer permission;

    /**
     * 时间
     */
    private LocalDateTime time;
}
