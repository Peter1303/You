package top.pdev.you.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.pdev.you.domain.entity.Campus;
import top.pdev.you.domain.ui.dto.CampusInfoDTO;

/**
 * 校区组装器
 * Created in 2022/11/2 17:05
 *
 * @author Peter1303
 */
@Mapper
public interface CampusAssembler {
    CampusAssembler INSTANCE = Mappers.getMapper(CampusAssembler.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CampusInfoDTO convert(Campus campusDO);
}
