package top.pdev.you.common.entity.role;

import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;

/**
 * 用户角色
 * Created in 2023/1/2 22:35
 *
 * @author Peter1303
 */
@Data
public abstract class RoleEntity extends BaseEntity {
    private String name;
    private String no;
    private String contact;
}
