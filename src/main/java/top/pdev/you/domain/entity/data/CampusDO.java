package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 校区
 * Created in 2022/10/1 14:21
 *
 * @author Peter1303
 */
@TableName("campus")
@Data
public class CampusDO {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;
}
