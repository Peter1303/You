package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;

/**
 * 社团
 * Created in 2022/10/24 14:04
 *
 * @author Peter1303
 */
@Data
public class Association extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */

    private String summary;
}
