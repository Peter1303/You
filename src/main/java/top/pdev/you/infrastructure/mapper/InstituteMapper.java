package top.pdev.you.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.pdev.you.domain.entity.Institute;
import top.pdev.you.domain.ui.dto.InstituteInfoDTO;

/**
 * 学院组装器
 * Created in 2022/11/2 16:57
 *
 * @author Peter1303
 */
@Mapper
public interface InstituteMapper {
    InstituteMapper INSTANCE = Mappers.getMapper(InstituteMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    InstituteInfoDTO convert(Institute instituteDO);
}
