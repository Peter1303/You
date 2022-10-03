package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录
 * Created in 2022/10/2 16:08
 *
 * @author Peter1303
 */
@Data
public class UserLoginVO {
    @Length(min = 6, max = 64)
    @NotBlank(message = "临时凭证不可为空")
    private String code;
}
