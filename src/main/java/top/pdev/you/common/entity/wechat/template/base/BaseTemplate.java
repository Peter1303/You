package top.pdev.you.common.entity.wechat.template.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 模板
 * Created in 2022/5/1 21:14
 *
 * @author Peter1303
 */
@Data
public abstract class BaseTemplate {
    /**
     * 模板 ID
     */
    @JsonIgnore
    private String id;

    /**
     * 获取模板信息
     *
     * @return 模板信息
     */
    public abstract BaseTemplateData getTemplateData();
}
