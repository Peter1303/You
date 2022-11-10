package top.pdev.you.infrastructure.config;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.pdev.you.common.exception.ConfigurationException;

/**
 * 微信模板配置
 * Created in 2022/4/21 19:10
 *
 * @author Peter1303
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wechat.lightapp.template")
public class WechatTemplateProperties implements InitializingBean {
    private String templateActivityAudit;
    private String templateAudit;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StrUtil.isAllNotBlank(templateActivityAudit, templateAudit)) {
            throw new ConfigurationException();
        }
    }
}
