package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;

/**
 * 班级领域
 * Created in 2022/10/3 18:18
 *
 * @author Peter1303
 */
@TableName("class")
@Data
public class Clazz extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学院 ID
     */
    private Long instituteId;

    /**
     * 校区 ID
     */
    private Long campusId;

    /**
     * 名称
     */
    private String name;

    /**
     * 年级
     */
    private Integer year;
}
