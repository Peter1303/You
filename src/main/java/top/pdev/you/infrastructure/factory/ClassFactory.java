package top.pdev.you.infrastructure.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Clazz;

/**
 * 班级工厂
 * Created in 2022/10/26 10:18
 *
 * @author Peter1303
 */
@Component
public class ClassFactory {
    /**
     * 获取班级
     *
     * @return {@link Clazz}
     */
    public Clazz newClazz() {
        return new Clazz();
    }
}
