package top.pdev.you.infrastructure.mapper;

import javax.annotation.Generated;
import top.pdev.you.domain.entity.Institute;
import top.pdev.you.domain.ui.dto.InstituteInfoDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-24T20:19:58+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
public class InstituteAssemblerImpl implements InstituteAssembler {

    @Override
    public InstituteInfoDTO convert(Institute instituteDO) {
        if ( instituteDO == null ) {
            return null;
        }

        InstituteInfoDTO instituteInfoDTO = new InstituteInfoDTO();

        instituteInfoDTO.setId( instituteDO.getId() );
        instituteInfoDTO.setName( instituteDO.getName() );

        return instituteInfoDTO;
    }
}
