package top.pdev.you.domain.entity;

import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.types.Id;

import java.util.Optional;

/**
 * 社团
 * Created in 2022/10/24 14:04
 *
 * @author Peter1303
 */
@Data
public class Association extends BaseEntity {
    private Id id;

    private String name;

    private String summary;

    private String logo;

    public Association(AssociationDO associationDO) {
        if (!Optional.ofNullable(associationDO).isPresent()) {
            return;
        }
        this.id = new Id(associationDO.getId());
        this.name = associationDO.getName();
        this.summary = associationDO.getSummary();
        this.logo = associationDO.getLogo();
    }
}
