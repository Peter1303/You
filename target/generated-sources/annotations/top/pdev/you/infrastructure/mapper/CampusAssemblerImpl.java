package top.pdev.you.infrastructure.mapper;

import javax.annotation.Generated;
import top.pdev.you.domain.entity.Campus;
import top.pdev.you.domain.ui.dto.CampusInfoDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-24T17:20:33+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
public class CampusAssemblerImpl implements CampusAssembler {

    @Override
    public CampusInfoDTO convert(Campus campusDO) {
        if ( campusDO == null ) {
            return null;
        }

        CampusInfoDTO campusInfoDTO = new CampusInfoDTO();

        campusInfoDTO.setId( campusDO.getId() );
        campusInfoDTO.setName( campusDO.getName() );

        return campusInfoDTO;
    }
}
