package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import top.pdev.you.common.entity.role.RoleEntity;

/**
 * 老师领域
 * Created in 2022/10/3 16:24
 *
 * @author Peter1303
 */
@Data
public class Teacher extends RoleEntity {
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
