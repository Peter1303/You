package top.pdev.you.domain.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.data.AssociationDO;

/**
 * 社团工厂
 * Created in 2022/10/24 14:06
 *
 * @author Peter1303
 */
@Component
public class AssociationFactory {
    /**
     * 获取社团
     *
     * @return {@link Association}
     */
    public Association getAssociation() {
        return getAssociation(null);
    }

    /**
     * 获取社团
     *
     * @param associationDO 协会 DO
     * @return {@link Association}
     */
    public Association getAssociation(AssociationDO associationDO) {
        return new Association(associationDO);
    }
}
