package top.pdev.you.interfaces.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.pdev.you.domain.entity.data.ClassDO;
import top.pdev.you.interfaces.model.dto.ClassInfoDTO;

/**
 * 类转换器
 * Created in 2022/10/3 18:45
 *
 * @author Peter1303
 */
@Mapper
public interface ClassAssembler {
    ClassAssembler INSTANCE = Mappers.getMapper(ClassAssembler.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    ClassInfoDTO convert(ClassDO classDO);
}
