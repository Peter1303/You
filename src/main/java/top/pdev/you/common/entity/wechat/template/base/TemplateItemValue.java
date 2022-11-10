package top.pdev.you.common.entity.wechat.template.base;

import lombok.Data;

/**
 * 模板子项数据
 * Created in 2022/11/10 18:23
 *
 * @author Peter1303
 */
@Data
public class TemplateItemValue {
    private String value = "";

    public TemplateItemValue() {
    }

    public TemplateItemValue(String value) {
        this.value = value;
    }
}
