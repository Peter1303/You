package top.pdev.you.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.model.dto.AssociationBaseInfoDTO;

/**
 * 社团组装器
 * Created in 2022/10/24 19:00
 *
 * @author Peter1303
 */
@Mapper
public interface AssociationMapper {
    AssociationMapper INSTANCE = Mappers.getMapper(AssociationMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "summary", target = "summary")
    AssociationBaseInfoDTO convert(Association associationDO);
}
