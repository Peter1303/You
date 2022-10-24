package top.pdev.you.interfaces.model.dto;

import lombok.Data;

/**
 * 社团信息 DTO
 * Created in 2022/10/24 19:07
 *
 * @author Peter1303
 */
@Data
public class AssociationInfoDTO {
    private Long id;
    private String name;
    private String summary;
    private String logo;
    private Integer type;
}
