package top.pdev.you.domain.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Clazz;
import top.pdev.you.domain.entity.data.ClassDO;

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
        return getClazz(null);
    }

    /**
     * 获取班级
     *
     * @param classDO 类 DO
     * @return {@link Clazz}
     */
    public Clazz getClazz(ClassDO classDO) {
        return new Clazz(classDO);
    }
}
