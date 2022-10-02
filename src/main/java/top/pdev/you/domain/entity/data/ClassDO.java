package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 班级
 * Created in 2022/10/1 14:19
 *
 * @author Peter1303
 */
@TableName("class")
@Data
public class ClassDO {
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
