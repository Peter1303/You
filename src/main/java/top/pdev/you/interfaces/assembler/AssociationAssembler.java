package top.pdev.you.interfaces.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.interfaces.model.dto.AssociationNameDTO;

/**
 * 社团组装器
 * Created in 2022/10/24 19:00
 *
 * @author Peter1303
 */
@Mapper
public interface AssociationAssembler {
    AssociationAssembler INSTANCE = Mappers.getMapper(AssociationAssembler.class);

    @Mapping(source = "name", target = "name")
    AssociationNameDTO convert(AssociationDO associationDO);
}
