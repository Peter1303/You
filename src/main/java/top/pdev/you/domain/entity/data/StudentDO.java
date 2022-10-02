package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 学生
 * Created in 2022/9/30 22:36
 *
 * @author Peter1303
 */
@TableName("student")
@Data
public class StudentDO {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 班级 ID
     */
    private Long classId;

    /**
     * 学号
     */
    private String no;

    /**
     * 名字
     */
    private String name;

    /**
     * 联系
     */
    private String contact;
}
