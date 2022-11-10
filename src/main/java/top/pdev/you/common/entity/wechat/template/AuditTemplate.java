package top.pdev.you.common.entity.wechat.template;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import top.pdev.you.common.entity.wechat.template.base.BaseTemplate;
import top.pdev.you.common.entity.wechat.template.base.BaseTemplateData;
import top.pdev.you.common.entity.wechat.template.base.TemplateItemValue;
import top.pdev.you.common.entity.wechat.template.data.AuditTemplateData;
import top.pdev.you.infrastructure.config.WechatTemplateProperties;

/**
 * 审核模板消息
 * Created in 2022/11/10 16:41
 *
 * @author Peter1303
 */
@Data
public class AuditTemplate extends BaseTemplate {
    @JsonIgnore
    private String title;

    @JsonIgnore
    private String name;

    @JsonIgnore
    private String result;

    @JsonIgnore
    private final WechatTemplateProperties wechatTemplateProperties =
            SpringUtil.getBean(WechatTemplateProperties.class);

    public AuditTemplate() {
        setId(wechatTemplateProperties.getTemplateAudit());
    }

    @Override
    public BaseTemplateData getTemplateData() {
        AuditTemplateData data = new AuditTemplateData();
        data.setTitle(new TemplateItemValue(title));
        data.setMember(new TemplateItemValue(name));
        data.setResult(new TemplateItemValue(result));
        return data;
    }
}
