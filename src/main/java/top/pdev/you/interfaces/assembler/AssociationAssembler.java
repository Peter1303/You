package top.pdev.you.interfaces.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.interfaces.model.dto.AssociationAuditDTO;
import top.pdev.you.interfaces.model.dto.AssociationBaseInfoDTO;
import top.pdev.you.interfaces.model.dto.AssociationInfoDTO;
import top.pdev.you.interfaces.model.vo.AssociationAuditVO;
import top.pdev.you.interfaces.model.vo.AssociationInfoVO;

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

    @Mapping(target = "status", ignore = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "summary", target = "summary")
    @Mapping(source = "numbers", target = "numbers")
    AssociationInfoVO convert2infoVO(AssociationInfoDTO infoDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "time", source = "time")
    AssociationAuditVO convert2auditInfoVO(AssociationAuditDTO auditDTO);
}
