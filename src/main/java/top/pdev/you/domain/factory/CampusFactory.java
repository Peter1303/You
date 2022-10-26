package top.pdev.you.domain.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Campus;
import top.pdev.you.domain.entity.data.CampusDO;

/**
 * 校区工厂
 * Created in 2022/10/26 11:17
 *
 * @author Peter1303
 */
@Component
public class CampusFactory {
    /**
     * 创建校区领域对象
     *
     * @return {@link Campus}
     */
    public Campus newCampus() {
        return getCampus(null);
    }

    /**
     * 获取校区领域对象
     *
     * @param campusDO 校园 DO
     * @return {@link Campus}
     */
    public Campus getCampus(CampusDO campusDO) {
        return new Campus(campusDO);
    }
}
