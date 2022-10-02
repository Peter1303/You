package top.pdev.you.infrastructure.config;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.pdev.you.common.exception.ConfigurationException;

/**
 * 缓存配置
 * Created in 2022/5/1 14:02
 *
 * @author Peter1303
 */
@Data
@Configuration
@ConfigurationProperties("cache")
public class CacheProperties implements InitializingBean {
    public String prefix;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StrUtil.isBlank(prefix)) {
            throw new ConfigurationException();
        }
    }
}
