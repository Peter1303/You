package top.pdev.you.common.entity.wechat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import top.pdev.you.common.entity.wechat.template.base.BaseTemplateData;

/**
 * 微信模板
 * Created in 2022/5/2 12:51
 *
 * @author Peter1303
 */
@Data
public class WechatTemplateData {
    @JsonProperty("touser")
    private String toUser;

    @JsonProperty("template_id")
    private String templateId;

    @JsonProperty("data")
    private BaseTemplateData templateData;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("page")
    private String pageUrl;
}
