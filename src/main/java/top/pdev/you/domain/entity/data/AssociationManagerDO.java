package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 社团管理
 * Created in 2022/10/1 14:10
 *
 * @author Peter1303
 */
@TableName("association_manager")
@Data
public class AssociationManagerDO {
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
     * uid
     */
    private Long uid;

    /**
     * 类型
     */
    private Integer type;
}
