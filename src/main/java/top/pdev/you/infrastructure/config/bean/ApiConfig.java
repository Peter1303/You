package top.pdev.you.infrastructure.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.pdev.you.application.resover.CurrentUserMethodArgumentResolver;
import top.pdev.you.common.interceptor.TokenInterceptor;

import javax.annotation.Resource;
import java.util.List;

/**
 * 配置
 * Created in 2022/4/19 18:38
 *
 * @author Peter1303
 */
@EnableWebMvc
@Configuration
public class ApiConfig implements WebMvcConfigurer {
    @Resource
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    @Resource
    private TokenInterceptor tokenInterceptor;

    /**
     * @return 令牌检查拦截器
     */
    @Bean
    public TokenInterceptor getTokenInterceptor() {
        return tokenInterceptor;
    }

    /**
     * 拦截器
     *
     * @param registry 注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/register/**",
                        "/user/login"
                );
    }

    /**
     * 添加参数解析器
     *
     * @param resolvers 解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 当前用户参数解析器
        resolvers.add(currentUserMethodArgumentResolver);
    }
}
