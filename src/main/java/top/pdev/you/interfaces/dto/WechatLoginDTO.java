package top.pdev.you.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信登录 DTO
 * Created in 2022/10/2 15:57
 *
 * @author Peter1303
 */
@Data
public class WechatLoginDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errcode")
    private String errCode;

    @JsonProperty("openid")
    private String openId;
}
