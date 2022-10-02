package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 老师
 * Created in 2022/9/30 22:41
 *
 * @author Peter1303
 */
@TableName("teacher")
@Data
public class TeacherDO {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 工号
     */
    private String no;

    /**
     * 联系
     */
    private String contact;
}
