package top.pdev.you.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 跳过登录检查
 * Created in 2022/8/19 19:08
 *
 * @author Peter1303
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipCheckLogin {
    boolean check() default true;
}
