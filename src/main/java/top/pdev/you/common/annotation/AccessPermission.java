package top.pdev.you.common.annotation;

import top.pdev.you.common.enums.Permission;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 访问权限
 * Created in 2022/8/19 22:26
 *
 * @author Peter1303
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessPermission {
    Permission permission() default Permission.USER;

    /**
     * 需要比目标权限低
     *
     * @return boolean
     */
    boolean lower() default false;
}
