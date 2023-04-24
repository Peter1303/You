package top.pdev.you.infrastructure.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.AssociationManager;

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
    public Association newAssociation() {
        return new Association();
    }

    /**
     * 新社团管理领域
     *
     * @return {@link AssociationManager}
     */
    public AssociationManager newAssociationManger() {
        return new AssociationManager();
    }
}
