package top.pdev.you.infrastructure.mapper;

import javax.annotation.Generated;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.ui.dto.AssociationBaseInfoDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-24T17:20:33+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
public class AssociationAssemblerImpl implements AssociationAssembler {

    @Override
    public AssociationBaseInfoDTO convert(Association associationDO) {
        if ( associationDO == null ) {
            return null;
        }

        AssociationBaseInfoDTO associationBaseInfoDTO = new AssociationBaseInfoDTO();

        associationBaseInfoDTO.setId( associationDO.getId() );
        associationBaseInfoDTO.setName( associationDO.getName() );
        associationBaseInfoDTO.setSummary( associationDO.getSummary() );

        return associationBaseInfoDTO;
    }
}
