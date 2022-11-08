package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.domain.repository.AssociationRepository;

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

    @Getter(AccessLevel.NONE)
    private final AssociationRepository associationRepository =
            SpringUtil.getBean(AssociationRepository.class);

    public Association(AssociationDO associationDO) {
        if (!Optional.ofNullable(associationDO).isPresent()) {
            return;
        }
        this.id = new Id(associationDO.getId());
        this.name = associationDO.getName();
        this.summary = associationDO.getSummary();
    }

    /**
     * 保存
     *
     * @param associationDO 协会 DO
     */
    public void save(AssociationDO associationDO) {
        // 查找是否已经存在社团
        if (associationRepository.exists(associationDO.getName())) {
            throw new BusinessException("已经存在相同的社团");
        }
        if (!associationRepository.save(associationDO)) {
            throw new BusinessException("无法保存社团");
        }
        setId(new Id(associationDO.getId()));
        setName(associationDO.getName());
        setSummary(associationDO.getSummary());
    }
}
