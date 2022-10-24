package top.pdev.you.infrastructure.config.bean;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.pdev.you.infrastructure.util.SpringEnvHelper;

/**
 * 配置
 * Created in 2022/10/2 19:52
 *
 * @author Peter1303
 */
@Configuration
public class YouConfig {
    @Bean
    public SpringEnvHelper getSpringEnvHelper() {
        return new SpringEnvHelper();
    }

    /**
     * 分页
     *
     * @return 分页拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
