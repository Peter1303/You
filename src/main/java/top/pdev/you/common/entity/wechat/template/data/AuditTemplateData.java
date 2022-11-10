package top.pdev.you.common.entity.wechat.template.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import top.pdev.you.common.entity.wechat.template.base.BaseTemplateData;
import top.pdev.you.common.entity.wechat.template.base.TemplateItemValue;

/**
 * 审核模板数据
 * Created in 2022/11/10 18:26
 *
 * @author Peter1303
 */
@Data
public class AuditTemplateData extends BaseTemplateData {
    /**
     * 事务名称
     */
    @JsonProperty("thing1")
    private TemplateItemValue title = new TemplateItemValue();

    /**
     * 审批人
     */
    @JsonProperty("thing6")
    private TemplateItemValue member = new TemplateItemValue();

    /**
     * 审核结果
     */
    @JsonProperty("phrase3")
    private TemplateItemValue result = new TemplateItemValue();
}
