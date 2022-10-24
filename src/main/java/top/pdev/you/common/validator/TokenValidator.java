package top.pdev.you.common.validator;

import cn.hutool.core.util.StrUtil;
import top.pdev.you.common.validator.annotation.Token;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 令牌验证
 * Created in 2022/8/14 18:44
 *
 * @author Peter1303
 */
public class TokenValidator
        implements ConstraintValidator<Token, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isBlankIfStr(value)) {
            return false;
        }
        return value.matches("^[a-zA-Z-\\d]{28}$");
    }
}
