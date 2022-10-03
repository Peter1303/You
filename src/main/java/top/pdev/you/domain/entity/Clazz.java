package top.pdev.you.domain.entity;

import lombok.Data;
import top.pdev.you.domain.entity.data.ClassDO;

import java.util.Optional;

/**
 * 班级领域
 * Created in 2022/10/3 18:18
 *
 * @author Peter1303
 */
@Data
public class Clazz {
    private Long id;

    public Clazz(ClassDO classDO) {
        if (!Optional.ofNullable(classDO).isPresent()) {
            return;
        }
        this.id = classDO.getId();
    }
}
