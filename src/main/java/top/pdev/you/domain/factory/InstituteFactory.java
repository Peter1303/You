package top.pdev.you.domain.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Institute;
import top.pdev.you.domain.entity.data.InstituteDO;

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
        return getInstitute(null);
    }

    /**
     * 获取学院
     *
     * @param instituteDO 研究所 DO
     * @return {@link Institute}
     */
    public Institute getInstitute(InstituteDO instituteDO) {
        return new Institute(instituteDO);
    }
}
