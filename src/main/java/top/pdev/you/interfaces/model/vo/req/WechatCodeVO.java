package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 微信临时凭证 VO
 * Created in 2022/10/3 15:22
 *
 * @author Peter1303
 */
@Data
public class WechatCodeVO {
    @Length(min = 6, max = 64)
    @NotBlank(message = "临时凭证不可为空")
    private String code;
}
