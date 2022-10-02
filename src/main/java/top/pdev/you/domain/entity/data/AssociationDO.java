package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 社团
 * Created in 2022/10/1 14:03
 *
 * @author Peter1303
 */
@TableName("association")
@Data
public class AssociationDO {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标志
     */
    private String logo;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String summary;
}
