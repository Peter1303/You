package top.pdev.you.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 社团信息 DTO
 * Created in 2022/10/24 19:07
 *
 * @author Peter1303
 */
@Data
public class AssociationBaseInfoDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String summary;
}
