package top.pdev.you.common.validator.annotation;

import top.pdev.you.common.validator.TokenValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 令牌
 * Created in 2022/8/14 18:43
 *
 * @author Peter1303
 */
@Target({ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {TokenValidator.class})
public @interface Token {
    String message() default "令牌非法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
