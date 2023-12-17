package top.pdev.you.infrastructure.config.bean;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 知识库配置
 * Created in 2023/12/15 23:39
 *
 * @author Peter1303
 */
@Data
@ConfigurationProperties("ai")
@Configuration
public class AiConfig implements InitializingBean {
    private String token;
    private String appId;
    private String baseApi;
    private String knowledgeId;

    public String getDataSetApi() {
        return getBaseApi() + "/core/dataset";
    }

    public String getApi() {
        return getBaseApi() + "/openapi/v1";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
