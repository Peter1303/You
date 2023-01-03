package top.pdev.you.common.entity.role;

import lombok.Data;

/**
 * 超级管理员
 * Created in 2023/1/3 10:33
 *
 * @author Peter1303
 */
@Data
public class SuperEntity extends RoleEntity {
    private String name = "超级管理员";
}
