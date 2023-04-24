package top.pdev.you.infrastructure.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.ui.dto.AssociationBaseInfoDTO;

/**
 * 社团组装器
 * Created in 2022/10/24 19:00
 *
 * @author Peter1303
 */
@Mapper
public interface AssociationAssembler {
    AssociationAssembler INSTANCE = Mappers.getMapper(AssociationAssembler.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "summary", target = "summary")
    AssociationBaseInfoDTO convert(Association associationDO);
}
