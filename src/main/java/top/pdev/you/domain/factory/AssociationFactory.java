package top.pdev.you.domain.factory;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.types.AssociationId;
import top.pdev.you.domain.repository.AssociationRepository;

import javax.annotation.Resource;

/**
 * 社团工厂
 * Created in 2022/10/24 14:06
 *
 * @author Peter1303
 */
@Component
public class AssociationFactory {
    @Lazy
    @Resource
    private AssociationRepository associationRepository;

    /**
     * 获取社团
     *
     * @return {@link Association}
     */
    public Association newAssociation() {
        return getAssociation((AssociationDO) null);
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

    /**
     * 获取社团
     *
     * @param id ID
     * @return {@link Association}
     */
    public Association getAssociation(AssociationId id) {
        return associationRepository.getOne(id);
    }
}
