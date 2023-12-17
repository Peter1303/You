package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import top.pdev.you.common.entity.role.RoleEntity;

/**
 * 学生领域
 * Created in 2022/10/3 18:11
 *
 * @author Peter1303
 */
@Data
public class Student extends RoleEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户 ID
     */
    private Long userId;

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
