package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;

/**
 * 社团管理领域
 * Created in 2023/1/3 16:45
 *
 * @author Peter1303
 */
@Data
public class AssociationManager extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 社团 ID
     */
    private Long associationId;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 类型
     */
    private Integer type;
}
