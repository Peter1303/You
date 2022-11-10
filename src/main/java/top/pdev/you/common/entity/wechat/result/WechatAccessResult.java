package top.pdev.you.common.entity.wechat.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.pdev.you.common.entity.wechat.result.base.WechatBaseResult;

/**
 * 微信调用凭证结果
 * Created in 2022/5/2 12:04
 *
 * @author Peter1303
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class WechatAccessResult extends WechatBaseResult {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Long expiresIn;
}
