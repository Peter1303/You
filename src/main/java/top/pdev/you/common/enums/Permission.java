package top.pdev.you.common.enums;

import lombok.Getter;

/**
 * 访问权限
 * Created in 2022/8/19 22:26
 *
 * @author Peter1303
 */
public enum Permission {
    /**
     * 用户
     */
    USER(10),
    /**
     * 负责人
     */
    MANAGER(20),
    /**
     * 管理
     */
    ADMIN(30),
    /**
     * 超管
     */
    SUPER(50);

    @Getter
    private final int value;
    Permission(int value) {
        this.value = value;
    }
}
