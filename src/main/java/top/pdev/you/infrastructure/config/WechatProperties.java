package top.pdev.you.infrastructure.config;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.pdev.you.common.exception.ConfigurationException;

/**
 * 微信配置
 * Created in 2022/10/2 16:02
 *
 * @author Peter1303
 */
@Data
@Configuration
@ConfigurationProperties("wechat")
public class WechatProperties implements InitializingBean {
    private String appId;
    private String secretKey;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StrUtil.isAllNotBlank(appId, secretKey)) {
            throw new ConfigurationException("微信配置未完全");
        }
    }
}
