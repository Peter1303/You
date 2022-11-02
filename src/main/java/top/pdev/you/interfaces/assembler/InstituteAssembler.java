package top.pdev.you.interfaces.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.pdev.you.domain.entity.data.InstituteDO;
import top.pdev.you.interfaces.model.dto.InstituteInfoDTO;

/**
 * 学院组装器
 * Created in 2022/11/2 16:57
 *
 * @author Peter1303
 */
@Mapper
public interface InstituteAssembler {
    InstituteAssembler INSTANCE = Mappers.getMapper(InstituteAssembler.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    InstituteInfoDTO convert(InstituteDO instituteDO);
}
