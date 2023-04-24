package top.pdev.you.infrastructure.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Institute;

/**
 * 学院工厂
 * Created in 2022/10/26 11:31
 *
 * @author Peter1303
 */
@Component
public class InstituteFactory {
    /**
     * 新学院领域
     *
     * @return {@link Institute}
     */
    public Institute newInstitute() {
        return new Institute();
    }
}
